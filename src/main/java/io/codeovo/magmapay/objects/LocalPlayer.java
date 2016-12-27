package io.codeovo.magmapay.objects;

public class LocalPlayer {
    private String stripeToken;
    private String pinHash;

    public LocalPlayer() {}

    public LocalPlayer(String stripeToken, String pinHash) {
        this.stripeToken = stripeToken;
        this.pinHash = pinHash;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }
}
