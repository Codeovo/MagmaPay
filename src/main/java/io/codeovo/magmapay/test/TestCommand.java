package io.codeovo.magmapay.test;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.charges.ChargeRequest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private MagmaPay magmaPay;

    public TestCommand(MagmaPay magmaPay) { this.magmaPay = magmaPay; }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        final Player p = (Player) commandSender;

        Bukkit.getScheduler().runTaskAsynchronously(magmaPay, new Runnable() {
            @Override
            public void run() {
                MagmaPay.getMagmaPayAPI().chargePlayer(new ChargeRequest(p, 2000, "CAD", true,
                        "test", "test", null));
            }
        });

        return false;
    }
}
