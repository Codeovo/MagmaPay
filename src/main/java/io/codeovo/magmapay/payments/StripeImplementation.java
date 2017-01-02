package io.codeovo.magmapay.payments;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Balance;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import com.stripe.model.Money;
import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.balance.AccountBalance;
import io.codeovo.magmapay.objects.charges.ChargeResponse;
import io.codeovo.magmapay.objects.charges.EarlyFailStatus;
import io.codeovo.magmapay.objects.registerplayer.RegisterPlayer;
import io.codeovo.magmapay.prompts.createuser.CreateUserProgressObject;

import java.util.HashMap;
import java.util.Map;

public class StripeImplementation {
    public StripeImplementation (MagmaPay magmaPay) {
        Stripe.apiKey = magmaPay.getLocalConfig().getStripeApiKey();
    }

    public static ChargeResponse captureCharge(String chargeID) {
        try {
            Charge c = Charge.retrieve(chargeID);
            c.capture();

            return new ChargeResponse(c.getId(), c.getStatus(), c.getCaptured(), c.getCreated(),
                    c.getFailureCode(), c.getFailureMessage(), c.getFraudDetails().getStripeReport(),
                    c.getFraudDetails().getUserReport());
        } catch (AuthenticationException e) {
            return new ChargeResponse(EarlyFailStatus.STRIPE_AUTHENTICATION_EXCEPTION);
        } catch (InvalidRequestException e) {
            return new ChargeResponse(EarlyFailStatus.STRIPE_INVALID_REQUEST_EXCEPTION);
        } catch (CardException e) {
            return new ChargeResponse(EarlyFailStatus.STRIPE_CARD_EXCEPTION);
        } catch (APIConnectionException e) {
            return new ChargeResponse(EarlyFailStatus.STRIPE_API_CONNECTION_EXCEPTION);
        } catch (APIException e) {
            return new ChargeResponse(EarlyFailStatus.STRIPE_API_EXCEPTION);
        }
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

    public static String createCustomerAPI(RegisterPlayer registerPlayer)
            throws CardException, APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("description", "Customer for " + registerPlayer.getEmail());
        customerParams.put("email", registerPlayer.getEmail());

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("object", "card");

        if (registerPlayer.getAddress() != null) {
            cardParams.put("address_line_1", registerPlayer.getAddress());
            cardParams.put("address_city", registerPlayer.getCity());
            cardParams.put("address_state", registerPlayer.getStateOrProvince());
            cardParams.put("address_zip", registerPlayer.getZip());
            cardParams.put("address_country", registerPlayer.getCountry());
        }

        cardParams.put("name", registerPlayer.getCardName());
        cardParams.put("number", registerPlayer.getCardNumber());
        cardParams.put("exp_month", registerPlayer.getCardMonth());
        cardParams.put("exp_year", registerPlayer.getCardYear());
        cardParams.put("cvc", registerPlayer.getCardCVC());

        customerParams.put("source", cardParams);

        Customer c = Customer.create(customerParams);
        return c.getId();
    }

    public static AccountBalance getBalances() {
        try {
            Balance balance = Balance.retrieve();

            HashMap<String, Long> availableBalances = new HashMap<>();
            for (Money money : balance.getAvailable()) {
                availableBalances.put(money.getCurrency(), money.getAmount());
            }

            HashMap<String, Long> pendingBalances = new HashMap<>();
            for (Money money : balance.getPending()) {
                pendingBalances.put(money.getCurrency(), money.getAmount());
            }

            return new AccountBalance(balance.getLivemode(), availableBalances, pendingBalances);
        } catch (AuthenticationException | InvalidRequestException | APIConnectionException
                | CardException | APIException e) {
            return null;
        }
    }
}
