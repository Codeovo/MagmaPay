package io.codeovo.magmapay.objects.charges;

import org.bukkit.entity.Player;

public class ChargeRequest {
    private Player player;
    private int amountToCharge;
    private String isoCurrency;
    private boolean chargeImmediately;
    private String chargeDescription;
    private String statementDescriptor;

    private String providedPin;

    public ChargeRequest(Player player, int amountToCharge, String isoCurrency, boolean chargeImmediately,
                         String chargeDescription, String statementDescriptor, String providedPin) {
        this.player = player;
        this.amountToCharge = amountToCharge;
        this.isoCurrency = isoCurrency;

        this.chargeDescription = chargeDescription;
        this.statementDescriptor = statementDescriptor;

        this.chargeImmediately = chargeImmediately;
        this.providedPin = providedPin;
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

    public String getProvidedPin() { return providedPin; }
}
