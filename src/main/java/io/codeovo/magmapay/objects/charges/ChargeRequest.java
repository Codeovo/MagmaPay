package io.codeovo.magmapay.objects.charges;

import org.bukkit.entity.Player;

public class ChargeRequest {
    private Player player;
    private int amountToCharge;
    private String isoCurrency;
    private boolean chargeImediately;
    private String chargeDescription;
    private String statementDescriptor;

    private boolean pinProvided;
    private String providedPin;

    private int applicationFee;
    private String destinationAccount;

    public ChargeRequest(Player player, int amountToCharge, String isoCurrency) {
        this.player = player;
        this.amountToCharge = amountToCharge;
        this.isoCurrency = isoCurrency;

        this.pinProvided = false;
        this.chargeImediately = true;
    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    public int getAmountToCharge() { return amountToCharge; }

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
