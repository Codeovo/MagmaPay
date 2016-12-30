package io.codeovo.magmapay.prompts.createuser;

import io.codeovo.magmapay.MagmaPay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class CreateUserListener implements Listener {
    private CreateUserManager createUserManager;

    CreateUserListener(CreateUserManager createUserManager) {
        this.createUserManager = createUserManager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(createUserManager.isInMap(e.getPlayer())) {
            e.setCancelled(true);

            createUserManager.handleMessage(e.getPlayer(), e.getMessage());
            return;
        }

        for(final Iterator<Player> it = e.getRecipients().iterator(); it.hasNext();) {

            final Player p = it.next();
            if(createUserManager.isInMap(p)) {
                it.remove();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(createUserManager.isInMap(e.getPlayer())) {
            createUserManager.removePlayer(e.getPlayer());

            MagmaPay.getMagmaPayAPI().getCustomerRetrievalHashMap().get(e.getPlayer()).countDown();
        }
    }
}
