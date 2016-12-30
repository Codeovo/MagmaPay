package io.codeovo.magmapay.test;

import io.codeovo.magmapay.MagmaPay;
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
        ChargeResponse chargeResponse = MagmaPay.getMagmaPayAPI().chargePlayer(new ChargeRequest((Player) commandSender,
                5000, "CAD", true, "Test Charge",
                "Test Charge", "3128"));

        commandSender.sendMessage(chargeResponse.getChargeId());
        commandSender.sendMessage(chargeResponse.getFailureCode());
        commandSender.sendMessage(chargeResponse.getFailureMessage());
        commandSender.sendMessage(chargeResponse.getStatus());
        commandSender.sendMessage(chargeResponse.getStripeFraudReport());
        commandSender.sendMessage(chargeResponse.getStripeUserReport());
        commandSender.sendMessage(String.valueOf(chargeResponse.getCreatedStamp()));
        commandSender.sendMessage(String.valueOf(chargeResponse.isCaptured()));

        return false;
    }
}
