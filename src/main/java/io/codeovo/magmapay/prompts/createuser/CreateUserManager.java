package io.codeovo.magmapay.prompts.createuser;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreateUserManager {
    private HashMap<Player, CreateUserStep> createUserMap;

    public CreateUserManager() {
        this.createUserMap = new HashMap<>();
    }

    public boolean isInMap(Player p) {
        return createUserMap.containsKey(p);
    }

    public CreateUserStep getPlayerStatus(Player p) {
        if (isInMap(p)) {
            return createUserMap.get(p);
        }

        return null;
    }

    public boolean addPlayer(Player p) {
        if (!isInMap(p)) {
            createUserMap.put(p, CreateUserStep.EMAIL);

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
        CreateUserStep nextStep = getPlayerStatus(p).next();

        if (nextStep == CreateUserStep.values()[0]) {
            removePlayer(p);

            return null;
        }

        createUserMap.put(p, nextStep);
        return nextStep;
    }
}
