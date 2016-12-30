package io.codeovo.magmapay.prompts.pinretrieval;

import io.codeovo.magmapay.MagmaPay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PinRetrievalManager {
    private MagmaPay magmaPay;

    private List<Player> pinRetrievalList;

    public PinRetrievalManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.pinRetrievalList = new ArrayList<>();

        Bukkit.getServer().getPluginManager()
                .registerEvents(new PinRetrievalListener(this), magmaPay);
    }

    void handleMessage(Player p, String message) {
        if (message.equalsIgnoreCase("quit") || message.equalsIgnoreCase("cancel")) {
            p.sendMessage(magmaPay.getLocalConfig().getMessagePinRetrieveCancel());

            removePlayer(p);
            MagmaPay.getMagmaPayAPI().getPinRetrievalHashMap().get(p).countDown();
            return;
        }

        MagmaPay.getMagmaPayAPI().getRetrievedPin().put(p, message);
        MagmaPay.getMagmaPayAPI().getPinRetrievalHashMap().get(p).countDown();
    }

    public void addPlayer(Player p) {
        pinRetrievalList.add(p);
        p.sendMessage(magmaPay.getLocalConfig().getMessagePinRetrieveEnterPin());
    }

    boolean isInList(Player p) { return pinRetrievalList.contains(p); }

    void removePlayer(Player p) { pinRetrievalList.remove(p); }
}
