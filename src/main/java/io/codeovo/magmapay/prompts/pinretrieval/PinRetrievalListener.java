package io.codeovo.magmapay.prompts.pinretrieval;

import io.codeovo.magmapay.MagmaPay;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class PinRetrievalListener implements Listener {
    private PinRetrievalManager pinRetrievalManager;

    PinRetrievalListener(PinRetrievalManager pinRetrievalManager) {
        this.pinRetrievalManager = pinRetrievalManager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(pinRetrievalManager.isInList(e.getPlayer())) {
            e.setCancelled(true);

            pinRetrievalManager.handleMessage(e.getPlayer(), e.getMessage());
            return;
        }

        for(final Iterator<Player> it = e.getRecipients().iterator(); it.hasNext();) {

            final Player p = it.next();
            if(pinRetrievalManager.isInList(p)) {
                it.remove();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(pinRetrievalManager.isInList(e.getPlayer())) {
            pinRetrievalManager.removePlayer(e.getPlayer());

            MagmaPay.getMagmaPayAPI().getPinRetrievalHashMap().get(e.getPlayer()).countDown();
        }
    }
}
