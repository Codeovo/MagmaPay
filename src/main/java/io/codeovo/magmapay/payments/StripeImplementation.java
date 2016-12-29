package io.codeovo.magmapay.payments;

import com.stripe.Stripe;
import io.codeovo.magmapay.MagmaPay;

public class StripeImplementation {
    private MagmaPay magmaPay;

    public StripeImplementation (MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        Stripe.apiKey = magmaPay.getLocalConfig().getStripeApiKey();
    }
}
