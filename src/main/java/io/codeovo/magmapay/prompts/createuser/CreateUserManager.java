package io.codeovo.magmapay.prompts.createuser;

import com.stripe.exception.*;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;
import io.codeovo.magmapay.payments.StripeImplementation;
import io.codeovo.magmapay.utils.Encryption;
import io.codeovo.magmapay.utils.ValidationUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreateUserManager {
    private MagmaPay magmaPay;

    private HashMap<Player, CreateUserProgressObject> createUserMap;

    public CreateUserManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.createUserMap = new HashMap<>();

        Bukkit.getServer().getPluginManager().registerEvents(new CreateUserListener(this), magmaPay);
    }

    void handleMessage(final Player p, final String message) {
        final CreateUserProgressObject progressObject = getPlayerObject(p);
        CreateUserStep currentStep = progressObject.getUserStep();

        switch (currentStep) {
            case EMAIL:
                boolean isValidEmail = ValidationUtils.validateEmail(message);

                if (isValidEmail) {
                    progressObject.setEmail(message);
                    progressObject.setUserStep(CreateUserStep.PIN);

                    p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserPin());
                } else {
                    p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserEmailError());
                    removePlayer(p);
                }

                break;
            case PIN:
                boolean isValidPin = ValidationUtils.validatePin(message);

                if (isValidPin) {
                    Bukkit.getScheduler().runTaskAsynchronously(magmaPay, new Runnable() {
                        @Override
                        public void run() {
                            progressObject.setPinHash(Encryption.securePass(message));
                        }
                    });

                    if (magmaPay.getLocalConfig().isCollectBillingAddress()) {

                    } else {

                    }
                } else {
                    p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserPinError());
                    removePlayer(p);
                }

                break;
            case ADDRESS:
        }
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

    public void addPlayer(Player p) {
        createUserMap.put(p, new CreateUserProgressObject());
        p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserEmail());
    }

    public void removePlayer(Player p) {
        createUserMap.remove(p);
    }
}
