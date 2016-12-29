package io.codeovo.magmapay.objects.charges;

public class ChargeRequest {
    private int amountToCharge;
    private String isoCurrency;
    private boolean chargeImediately;
    private String chargeDescription;
    private String statementDescriptor;

    private int applicationFee;
    private String destinationAccount;

    public ChargeRequest(int amountToCharge, String isoCurrency) {
        this.amountToCharge = amountToCharge;
        this.isoCurrency = isoCurrency;
        this.chargeImediately = true;
    }

    public int getAmountToCharge() {
        return amountToCharge;
    }

    public void setAmountToCharge(int amountToCharge) {
        this.amountToCharge = amountToCharge;
    }

    public String getIsoCurrency() {
        return isoCurrency;
    }

    public void setIsoCurrency(String isoCurrency) {
        this.isoCurrency = isoCurrency;
    }

    public boolean isChargeImediately() {
        return chargeImediately;
    }

    public void setChargeImediately(boolean chargeImediately) {
        this.chargeImediately = chargeImediately;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public int getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(int applicationFee) {
        this.applicationFee = applicationFee;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
