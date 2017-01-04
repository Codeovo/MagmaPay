package io.codeovo.magmapay.api;

import com.stripe.exception.*;
import com.stripe.model.Charge;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;
import io.codeovo.magmapay.objects.balance.AccountBalance;
import io.codeovo.magmapay.objects.charges.ChargeDataResponse;
import io.codeovo.magmapay.objects.charges.ChargeRequest;
import io.codeovo.magmapay.objects.charges.ChargeResponse;
import io.codeovo.magmapay.objects.charges.EarlyFailStatus;
import io.codeovo.magmapay.objects.registerplayer.RegisterPlayer;
import io.codeovo.magmapay.payments.StripeImplementation;
import io.codeovo.magmapay.utils.Encryption;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MagmaPayAPI {
    private MagmaPay magmaPay;

    private ExecutorService executorService;

    public MagmaPayAPI(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        this.executorService = Executors.newCachedThreadPool();
    }

    // CHARGE RELATED METHODS

    public ChargeResponse chargePlayer(final ChargeRequest chargeRequest) {
        String stripeTokenId;

        if (!chargeRequest.getPlayer().isOnline()) {
            return new ChargeResponse(EarlyFailStatus.PLAYER_OFFLINE);
        }

        if (magmaPay.getCacheManager().getCustomerRetrievalHashMap().containsKey(chargeRequest.getPlayer())
                || magmaPay.getCacheManager().getPinRetrievalHashMap().containsKey(chargeRequest.getPlayer())) {
            return new ChargeResponse(EarlyFailStatus.COLLECTING_DATA_FROM_PREVIOUS_CHARGE);
        }

        if (!magmaPay.getCacheManager().isInCache(chargeRequest.getPlayer())) {
            magmaPay.getCacheManager().getCustomerRetrievalHashMap().put(chargeRequest.getPlayer(),
                    new CountDownLatch(1));

            try {
                magmaPay.getPromptManager().getCreateUserManager().addPlayer(chargeRequest.getPlayer());
                magmaPay.getCacheManager().getCustomerRetrievalHashMap().get(chargeRequest.getPlayer()).await();
            } catch (InterruptedException e) {
                magmaPay.getCacheManager().getCustomerRetrievalHashMap().remove(chargeRequest.getPlayer());
                return new ChargeResponse(EarlyFailStatus.DATA_RETRIEVAL_ERROR);
            }

            magmaPay.getCacheManager().getCustomerRetrievalHashMap().remove(chargeRequest.getPlayer());

            if (magmaPay.getCacheManager().isInCache(chargeRequest.getPlayer())) {
                stripeTokenId = magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getStripeToken();
            } else {
                return new ChargeResponse(EarlyFailStatus.FAIL_DURING_DATA_RETRIEVAL);
            }
        } else {
            stripeTokenId = magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getStripeToken();
        }

        String pin;

        if (chargeRequest.getProvidedPin() != null) {
            pin = chargeRequest.getProvidedPin();
        } else {
            magmaPay.getCacheManager().getPinRetrievalHashMap().put(chargeRequest.getPlayer(),
                    new CountDownLatch(  1));

            try {
                magmaPay.getPromptManager().getPinRetrievalManager().addPlayer(chargeRequest.getPlayer());
                magmaPay.getCacheManager().getPinRetrievalHashMap().get(chargeRequest.getPlayer()).await();
            } catch (InterruptedException e) {
                magmaPay.getCacheManager().getPinRetrievalHashMap().remove(chargeRequest.getPlayer());
                return new ChargeResponse(EarlyFailStatus.DATA_RETRIEVAL_ERROR);
            }

            magmaPay.getCacheManager().getPinRetrievalHashMap().remove(chargeRequest.getPlayer());

            if (magmaPay.getCacheManager().getRetrievedPin().containsKey(chargeRequest.getPlayer())) {
                pin = magmaPay.getCacheManager().getRetrievedPin().get(chargeRequest.getPlayer());
                magmaPay.getCacheManager().getRetrievedPin().remove(chargeRequest.getPlayer());
            } else {
                return new ChargeResponse(EarlyFailStatus.FAIL_DURING_DATA_RETRIEVAL);
            }
        }

        if (!(Encryption.isSame(magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getPinHash(), pin))) {
            return new ChargeResponse(EarlyFailStatus.INCORRECT_PIN);
        }

        final String finalStripeTokenId = stripeTokenId;

        Future<ChargeResponse> future = executorService.submit(() -> {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("customer", finalStripeTokenId);

            chargeParams.put("amount", chargeRequest.getAmountToCharge());
            chargeParams.put("currency", chargeRequest.getIsoCurrency());

            if (!chargeRequest.isChargeImmediately()) {
                chargeParams.put("capture", false);
            }

            chargeParams.put("description", chargeRequest.getChargeDescription());
            chargeParams.put("statement_descriptor", chargeRequest.getStatementDescriptor());

            Charge c =  Charge.create(chargeParams);

            if (c == null) {
                return new ChargeResponse(EarlyFailStatus.DATA_RETRIEVAL_ERROR);
            }

            return new ChargeResponse(c.getId(), c.getStatus(), c.getCaptured(), c.getCreated(),
                    c.getFailureCode(), c.getFailureMessage(), c.getFraudDetails().getStripeReport(),
                    c.getFraudDetails().getUserReport(), c);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new ChargeResponse(EarlyFailStatus.DATA_RETRIEVAL_ERROR);
    }

    public ChargeResponse captureCharge(String chargeID) { return StripeImplementation.captureCharge(chargeID); }

    public ChargeDataResponse getChargeInformation(String chargeID) {
        return StripeImplementation.retrieveCharge(chargeID);
    }

    // PLAYER RELATED METHODS

    public boolean checkIfPlayerIsRegistered(Player p) {
        return magmaPay.getCacheManager().isInCache(p);
    }

    public String getPlayerCustomerID(Player p) {
        if (magmaPay.getCacheManager().isInCache(p)) {
            return magmaPay.getCacheManager().getPlayer(p).getStripeToken();
        } else {
            return null;
        }
    }

    public void registerPlayer(Player p, RegisterPlayer toRegister) throws CardException, APIException,
            AuthenticationException, InvalidRequestException, APIConnectionException {
        if (!p.isOnline()) {
            return;
        }

        String customerID = StripeImplementation.createCustomerAPI(toRegister);

        magmaPay.getCacheManager()
                .addPlayer(p, new LocalPlayer(customerID, toRegister.getPin()));
    }

    // MAIN ACCOUNT RELATED METHODS

    public AccountBalance getBalances() { return StripeImplementation.getBalances(); }

    // PLUGIN RELATED METHODS

    public boolean areWebHooksEnabled() { return magmaPay.getLocalConfig().isUseWebHooks(); }
}
