package io.codeovo.magmapay.cache;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CacheManager {
    private MagmaPay magmaPay;
    private HashMap<Player, LocalPlayer> localCache;

    private HashMap<Player, CountDownLatch> customerRetrievalHashMap;

    private HashMap<Player, CountDownLatch> pinRetrievalHashMap;
    private HashMap<Player, String> retrievedPin;

    public CacheManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        this.localCache = new HashMap<>();

        this.customerRetrievalHashMap = new HashMap<>();

        this.pinRetrievalHashMap = new HashMap<>();
        this.retrievedPin = new HashMap<>();
    }

    public boolean isInCache(Player p) {
        return localCache.containsKey(p);
    }

    public LocalPlayer getPlayer(Player p) {
        if (isInCache(p)) {
            return localCache.get(p);
        }

        return null;
    }

    public boolean addPlayer(Player p, LocalPlayer localPlayer) {
        if (!isInCache(p)) {
            localCache.put(p, localPlayer);
            return true;
        }

        return false;
    }

    public boolean removePlayer(Player p) {
        if (isInCache(p)) {
            LocalPlayer localPlayer = getPlayer(p);

            magmaPay.getLocalStorage().addUser(p.getUniqueId(), localPlayer.getStripeToken(),
                    localPlayer.getPinHash());

            localCache.remove(p);
            return true;
        }

        return false;
    }

    public HashMap<Player, CountDownLatch> getCustomerRetrievalHashMap() {
        return customerRetrievalHashMap;
    }

    public HashMap<Player, CountDownLatch> getPinRetrievalHashMap() { return pinRetrievalHashMap; }

    public HashMap<Player, String> getRetrievedPin() { return retrievedPin; }

    public void shutdown() {
        for (Map.Entry<Player, LocalPlayer> entry : localCache.entrySet()) {
            LocalPlayer localPlayer = entry.getValue();
            Player p = entry.getKey();

            magmaPay.getLocalStorage().addUserLocal(p.getUniqueId(), localPlayer.getStripeToken(),
                    localPlayer.getPinHash());

            localCache.remove(p);
        }
    }
}
