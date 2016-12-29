package io.codeovo.magmapay.payments;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.prompts.createuser.CreateUserProgressObject;

import java.util.HashMap;
import java.util.Map;

public class StripeImplementation {
    private MagmaPay magmaPay;

    public StripeImplementation (MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        Stripe.apiKey = magmaPay.getLocalConfig().getStripeApiKey();
    }

    public static String createCustomer(CreateUserProgressObject createUserProgressObject)
            throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("description", "Customer for " + createUserProgressObject.getEmail());
        customerParams.put("email", createUserProgressObject.getEmail());

        Customer c = Customer.create(customerParams);

        return c.getId();
    }
}
