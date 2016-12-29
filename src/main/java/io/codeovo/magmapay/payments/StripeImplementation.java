package io.codeovo.magmapay.payments;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;

import io.codeovo.magmapay.MagmaPay;

import java.util.HashMap;
import java.util.Map;

public class StripeImplementation {
    private MagmaPay magmaPay;

    public StripeImplementation (MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        Stripe.apiKey = magmaPay.getLocalConfig().getStripeApiKey();
    }

    public static String createUser(String email) throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("description", "Customer for " + email);
        customerParams.put("email", email);

        Customer c = Customer.create(customerParams);

        return c.getId();
    }
}
