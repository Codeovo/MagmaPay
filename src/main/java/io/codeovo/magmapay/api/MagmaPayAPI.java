package io.codeovo.magmapay.api;

import com.stripe.model.Charge;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.charges.ChargeRequest;
import io.codeovo.magmapay.objects.charges.ChargeResponse;
import io.codeovo.magmapay.utils.Encryption;

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

    public ChargeResponse chargePlayer(final ChargeRequest chargeRequest) {
        String stripeTokenId = "";

        if (!chargeRequest.getPlayer().isOnline()) {
            return null;
        }

        if (!magmaPay.getCacheManager().isInCache(chargeRequest.getPlayer())) {

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
}
