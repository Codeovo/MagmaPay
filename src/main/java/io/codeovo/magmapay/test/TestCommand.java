package io.codeovo.magmapay.test;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;
import io.codeovo.magmapay.objects.charges.ChargeRequest;
import io.codeovo.magmapay.objects.charges.ChargeResponse;
import io.codeovo.magmapay.utils.Encryption;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private MagmaPay magmaPay;

    public TestCommand(MagmaPay magmaPay) { this.magmaPay = magmaPay; }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        MagmaPay.getMagmaPayAPI().chargePlayer(new ChargeRequest(p, 2000, "CAD", true,
                "test", "test", "3128"));

        return false;
    }
}
