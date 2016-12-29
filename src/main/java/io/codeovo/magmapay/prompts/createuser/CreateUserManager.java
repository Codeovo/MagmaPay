package io.codeovo.magmapay.prompts.createuser;

import io.codeovo.magmapay.MagmaPay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreateUserManager {
    private MagmaPay magmaPay;

    private HashMap<Player, CreateUserProgressObject> createUserMap;

    public CreateUserManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        Bukkit.getServer().getPluginManager().registerEvents(new CreateUserListener(), magmaPay);

        this.createUserMap = new HashMap<>();
    }



    public boolean isInMap(Player p) {
        return createUserMap.containsKey(p);
    }

    public CreateUserProgressObject getPlayerObject(Player p) {
        if (isInMap(p)) {
            return createUserMap.get(p);
        }

        return null;
    }

    public boolean addPlayer(Player p) {
        if (!isInMap(p)) {
            createUserMap.put(p, new CreateUserProgressObject());

            return true;
        }

        return false;
    }

    public boolean removePlayer(Player p) {
        if (isInMap(p)) {
            createUserMap.remove(p);

            return true;
        }

        return false;
    }

    public CreateUserStep updatePlayer(Player p) {
        CreateUserProgressObject userObject = getPlayerObject(p);
        CreateUserStep nextStep = userObject.getUserStep().next();

        if (nextStep == CreateUserStep.values()[0]) {
            return null;
        }

        userObject.setUserStep(nextStep);
        return nextStep;
    }
}
