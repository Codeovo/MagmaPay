package io.codeovo.magmapay.test;

import io.codeovo.magmapay.MagmaPay;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private MagmaPay magmaPay;

    public TestCommand(MagmaPay magmaPay) { this.magmaPay = magmaPay; }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        magmaPay.getPromptManager().getCreateUserManager().addPlayer((Player) commandSender);

        return false;
    }
}
