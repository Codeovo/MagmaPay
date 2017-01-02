package io.codeovo.magmapay.listeners;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private MagmaPay magmaPay;

    public PlayerListener(MagmaPay magmaPay) { this.magmaPay = magmaPay; }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(MagmaPay.getInstance(), () -> {
            LocalPlayer localPlayer = magmaPay.getLocalStorage().getPlayerData(e.getPlayer());

            if (localPlayer.getPinHash() != null && localPlayer.getStripeToken() != null) {
                magmaPay.getCacheManager().addPlayer(e.getPlayer(), localPlayer);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        magmaPay.getCacheManager().removePlayer(e.getPlayer());
    }
}
