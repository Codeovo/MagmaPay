package io.codeovo.magmapay.objects.charges;

import com.stripe.model.Charge;

public class ChargeResponse {
    private EarlyFailStatus earlyFailStatus;

    private String chargeId;
    private String status;
    private boolean captured;
    private long createdStamp;

    private String failureCode;
    private String failureMessage;

    private String stripeFraudReport;
    private String stripeUserReport;

    private Charge stripeChargeObject;

    public ChargeResponse(EarlyFailStatus earlyFailStatus) {
        this.earlyFailStatus = earlyFailStatus;
    }

    public ChargeResponse(String chargeId, String status, boolean captured,
                          long createdStamp, String failureCode, String failureMessage,
                          String stripeFraudReport, String stripeUserReport, Charge stripeChargeObject) {
        this.earlyFailStatus = null;

        this.chargeId = chargeId;
        this.status = status;
        this.captured = captured;
        this.createdStamp = createdStamp;

        this.failureCode = failureCode;
        this.failureMessage = failureMessage;

        this.stripeFraudReport = stripeFraudReport;
        this.stripeUserReport = stripeUserReport;

        this.stripeChargeObject = stripeChargeObject;
    }

    public EarlyFailStatus getEarlyFailStatus() { return earlyFailStatus; }

    public String getChargeId() { return chargeId; }

    public String getStatus() { return status; }

    public boolean isCaptured() { return captured; }

    public long getCreatedStamp() { return createdStamp; }

    public String getFailureCode() { return failureCode; }

    public String getFailureMessage() { return failureMessage; }

    public String getStripeFraudReport() { return stripeFraudReport; }

    public String getStripeUserReport() { return stripeUserReport; }

    public Charge getStripeChargeObject() { return stripeChargeObject; }
}
