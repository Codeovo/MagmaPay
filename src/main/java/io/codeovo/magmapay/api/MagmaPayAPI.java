package io.codeovo.magmapay.api;

import com.stripe.model.Charge;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.charges.ChargeRequest;
import io.codeovo.magmapay.objects.charges.ChargeResponse;
import io.codeovo.magmapay.objects.charges.EarlyFailStatus;
import io.codeovo.magmapay.utils.Encryption;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MagmaPayAPI {
    private MagmaPay magmaPay;

    private HashMap<Player, CountDownLatch> customerRetrievalHashMap;
    private ExecutorService executorService;

    public MagmaPayAPI(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        this.customerRetrievalHashMap = new HashMap<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public ChargeResponse chargePlayer(final ChargeRequest chargeRequest) {
        String stripeTokenId;

        if (!chargeRequest.getPlayer().isOnline()) {
            return new ChargeResponse(EarlyFailStatus.PLAYER_OFFLINE);
        }

        if (customerRetrievalHashMap.containsKey(chargeRequest.getPlayer())) {
            return new ChargeResponse(EarlyFailStatus.COLLECTING_DATA_FROM_PREVIOUS_CHARGE);
        }

        if (!magmaPay.getCacheManager().isInCache(chargeRequest.getPlayer())) {
            customerRetrievalHashMap.put(chargeRequest.getPlayer(), new CountDownLatch(1));
            try {
                magmaPay.getPromptManager().getCreateUserManager().addPlayer(chargeRequest.getPlayer());
                customerRetrievalHashMap.get(chargeRequest.getPlayer()).await();
            } catch (InterruptedException e) {
                return new ChargeResponse(EarlyFailStatus.DATA_RETRIEVAL_ERROR);
            }

            customerRetrievalHashMap.remove(chargeRequest.getPlayer());

            if (magmaPay.getCacheManager().isInCache(chargeRequest.getPlayer())) {
                stripeTokenId = magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getStripeToken();
            } else {
                return new ChargeResponse(EarlyFailStatus.FAIL_DURING_DATA_RETRIEVAL);
            }
        } else {
            stripeTokenId = magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getStripeToken();
        }

        String pin = "";

        if (chargeRequest.getProvidedPin() != null) {
            pin = chargeRequest.getProvidedPin();
        } else {

        }

        if (!(Encryption.isSame(magmaPay.getCacheManager().getPlayer(chargeRequest.getPlayer()).getPinHash(), pin))) {
            return null;
        }

        final String finalStripeTokenId = stripeTokenId;

        Future<ChargeResponse> future = executorService.submit(new Callable<ChargeResponse>() {
            @Override
            public ChargeResponse call() throws Exception {
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

                return new ChargeResponse(c.getId(), c.getStatus(), c.getCaptured(), c.getCreated(),
                        c.getFailureCode(), c.getFailureMessage(), c.getFraudDetails().getStripeReport(),
                        c.getFraudDetails().getUserReport());
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HashMap<Player, CountDownLatch> getCustomerRetrievalHashMap() {
        return customerRetrievalHashMap;
    }
}
