package io.codeovo.magmapay.objects.charges;

import org.bukkit.entity.Player;

public class ChargeRequest {
    private Player player;
    private int amountToCharge;
    private String isoCurrency;
    private boolean chargeImmediately;
    private String chargeDescription;
    private String statementDescriptor;

    private boolean pinProvided;
    private String providedPin;

    private int applicationFee;
    private String destinationAccount;

    public ChargeRequest(Player player, int amountToCharge, String isoCurrency, boolean chargeImmediately,
                         String chargeDescription, String statementDescriptor) {
        this.player = player;
        this.amountToCharge = amountToCharge;
        this.isoCurrency = isoCurrency;

        this.chargeDescription = chargeDescription;
        this.statementDescriptor = statementDescriptor;

        this.pinProvided = false;
        this.chargeImmediately = chargeImmediately;
    }

    public Player getPlayer() { return player; }

    public int getAmountToCharge() { return amountToCharge; }

    public String getIsoCurrency() {
        return isoCurrency;
    }

    public boolean isChargeImmediately() {
        return chargeImmediately;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public boolean isPinProvided() { return pinProvided; }

    public void setPinProvided(boolean pinProvided) { this.pinProvided = pinProvided; }

    public String getProvidedPin() { return providedPin; }

    public void setProvidedPin(String providedPin) { this.providedPin = providedPin; }

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
