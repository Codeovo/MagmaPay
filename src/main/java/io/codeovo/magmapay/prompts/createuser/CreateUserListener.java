package io.codeovo.magmapay.prompts.createuser;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;

public class CreateUserListener implements Listener {
    private CreateUserManager createUserManager;

    public CreateUserListener(CreateUserManager createUserManager) {
        this.createUserManager = createUserManager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(createUserManager.isInMap(e.getPlayer())) {
            e.setCancelled(true);



            return;
        }

        for(final Iterator<Player> it = e.getRecipients().iterator(); it.hasNext();) {

            final Player p = it.next();
            if(createUserManager.isInMap(p)) {
                it.remove();
            }
        }
    }
}
