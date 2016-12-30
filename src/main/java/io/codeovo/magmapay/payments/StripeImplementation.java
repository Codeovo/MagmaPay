package io.codeovo.magmapay.payments;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.prompts.createuser.CreateUserProgressObject;

import java.util.HashMap;
import java.util.Map;

public class StripeImplementation {
    public StripeImplementation (MagmaPay magmaPay) {
        Stripe.apiKey = magmaPay.getLocalConfig().getStripeApiKey();
    }

    public static String createCustomer(CreateUserProgressObject createUserProgressObject)
            throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("description", "Customer for " + createUserProgressObject.getEmail());
        customerParams.put("email", createUserProgressObject.getEmail());

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("object", "card");

        if (MagmaPay.getInstance().getLocalConfig().isCollectBillingAddress()) {
            cardParams.put("address_line_1", createUserProgressObject.getAddress());
            cardParams.put("address_city", createUserProgressObject.getCity());
            cardParams.put("address_state", createUserProgressObject.getStateOrProvince());
            cardParams.put("address_zip", createUserProgressObject.getZip());
            cardParams.put("address_country", createUserProgressObject.getCountry());
        }

        cardParams.put("name", createUserProgressObject.getCardName());
        cardParams.put("number", createUserProgressObject.getCardNumber());
        cardParams.put("exp_month", createUserProgressObject.getCardMonth());
        cardParams.put("exp_year", createUserProgressObject.getCardYear());
        cardParams.put("cvc", createUserProgressObject.getCardCVC());

        customerParams.put("source", cardParams);

        Customer c = Customer.create(customerParams);
        return c.getId();
    }
}
