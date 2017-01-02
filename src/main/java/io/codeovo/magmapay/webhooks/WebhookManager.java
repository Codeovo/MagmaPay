package io.codeovo.magmapay.webhooks;

import com.stripe.model.Dispute;
import io.codeovo.magmapay.MagmaPay;

import com.stripe.exception.StripeException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Event;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import org.apache.http.HttpStatus;

import java.util.logging.Level;

import static spark.Spark.post;

public class WebhookManager {
    private MagmaPay magmaPay;

    public WebhookManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        monitor();
    }

    private void monitor() {
        post(magmaPay.getLocalConfig().getWebhooksPath(), (request, response) -> {
            Event eventInJson;

            try {
                eventInJson = APIResource.GSON.fromJson(request.body(), Event.class);
            } catch (Exception e) {
                String failedError = "Webhook Error: " + e.getMessage();

                magmaPay.getLogger().log(Level.WARNING, "MagmaPay - " + failedError);
                response.status(HttpStatus.SC_BAD_REQUEST);

                return failedError;
            }

            // DEBUG REMOVE LATER
            magmaPay.getLogger().info("DEBUG: Received event: " + eventInJson.getId() +
                    ", type: " + eventInJson.getType() + ", user id: " + eventInJson.getUserId());

            Event event;
            try {
                RequestOptions requestOptions = null;
                if (eventInJson.getUserId() != null) {
                    requestOptions = RequestOptions.builder().setStripeAccount(eventInJson.getUserId()).build();
                }

                event = Event.retrieve(eventInJson.getId(), requestOptions);
            } catch (InvalidRequestException e) {
                String failedError = "Webhook Invalid Event: " + eventInJson.getId();

                magmaPay.getLogger().log(Level.WARNING, "MagmaPay - " + failedError);
                response.status(HttpStatus.SC_BAD_REQUEST);

                return failedError;
            } catch (StripeException e) {
                String failedError = "Webhook Stripe Error: " + e.getMessage();

                magmaPay.getLogger().log(Level.WARNING, "MagmaPay - " + failedError);
                response.status(e.getStatusCode());

                return failedError;
            }

            org.bukkit.event.Event toThrow;

            switch(event.getType()) {
                case "charge.dispute.created":
                    Dispute dispute = (Dispute) event.getData().getObject();
            }

            response.status(HttpStatus.SC_OK);
            return "";
        });
    }
}
