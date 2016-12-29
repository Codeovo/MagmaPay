package io.codeovo.magmapay.prompts.pinretrieval;

import io.codeovo.magmapay.MagmaPay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PinRetrievalManager {
    private MagmaPay magmaPay;

    public List<Player> pinRetrievalList;

    public PinRetrievalManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.pinRetrievalList = new ArrayList<>();

        Bukkit.getServer().getPluginManager()
                .registerEvents(new PinRetrievalListener(this), magmaPay);
    }

    boolean isInList(Player p) { return pinRetrievalList.contains(p); }

    void removePlayer(Player p) { pinRetrievalList.remove(p); }
}