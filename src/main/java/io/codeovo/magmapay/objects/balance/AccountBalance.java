package io.codeovo.magmapay.objects.balance;

import java.util.HashMap;

public class AccountBalance {
    private boolean isLiveMode;
    private HashMap<String, Long> availableBalances;
    private HashMap<String, Long> pendingBalances;

    public AccountBalance(boolean isLiveMode, HashMap<String, Long> availableBalances,
                          HashMap<String, Long> pendingBalances) {
        this.isLiveMode = isLiveMode;
        this.availableBalances = availableBalances;
        this.pendingBalances = pendingBalances;
    }

    public boolean isLiveMode() { return isLiveMode; }

    public HashMap<String, Long> getAvailableBalances() { return availableBalances; }

    public HashMap<String, Long> getPendingBalances() { return pendingBalances; }
}
