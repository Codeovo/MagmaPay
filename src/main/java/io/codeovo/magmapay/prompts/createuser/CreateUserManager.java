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
        if (message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("quit")) {
            p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserQuitSucessful());

            removePlayer(p);
            return;
        }

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
                        progressObject.setUserStep(CreateUserStep.ADDRESS);
                        p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserAddress());
                    } else {
                        progressObject.setUserStep(CreateUserStep.CC_NAME);
                        p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCardNumber());
                    }
                } else {
                    p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserPinError());
                    removePlayer(p);
                }

                break;
            case ADDRESS:
                progressObject.setAddress(message);
                progressObject.setUserStep(CreateUserStep.CITY);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCity());
                break;
            case CITY:
                progressObject.setCity(message);
                progressObject.setUserStep(CreateUserStep.STATE_PROVINCE);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserState());
                break;
            case STATE_PROVINCE:
                progressObject.setStateOrProvince(message);
                progressObject.setUserStep(CreateUserStep.ZIP);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserZip());
                break;
            case ZIP:
                progressObject.setZip(message);
                progressObject.setUserStep(CreateUserStep.COUNTRY);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCountry());
                break;
            case COUNTRY:
                progressObject.setCountry(message);
                progressObject.setUserStep(CreateUserStep.CC_NAME);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserName());
                break;
            case CC_NAME:
                progressObject.setCardName(message);
                progressObject.setUserStep(CreateUserStep.CC_NUMBER);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCardNumber());
                break;
            case CC_NUMBER:
                progressObject.setCardNumber(message);
                progressObject.setUserStep(CreateUserStep.CC_MONTH);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCardMonth());
                break;
            case CC_MONTH:
                progressObject.setCardMonth(message);
                progressObject.setUserStep(CreateUserStep.CC_YEAR);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCardYear());
                break;
            case CC_YEAR:
                progressObject.setCardYear(message);
                progressObject.setUserStep(CreateUserStep.CC_CVC);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCardCVC());
                break;
            case CC_CVC:
                progressObject.setCardCVC(message);

                p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCreating());
                removePlayer(p);

                Bukkit.getScheduler().runTaskAsynchronously(magmaPay, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String customerID = StripeImplementation.createCustomer(progressObject);

                            magmaPay.getCacheManager()
                                    .addPlayer(p, new LocalPlayer(customerID, progressObject.getPinHash()));

                            p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserCreated());
                        } catch (CardException | AuthenticationException e) {
                            p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserValidationError()
                                    .replace("<error>", e.getMessage()));

                            // REMOVE LATER
                            e.printStackTrace();
                        } catch (APIException | InvalidRequestException | APIConnectionException e) {
                            p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserStripeError()
                                    .replace("<error>", e.getMessage()));

                            // REMOVE LATER
                            e.printStackTrace();
                        }
                    }
                });

        }
    }

    boolean isInMap(Player p) {
        return createUserMap.containsKey(p);
    }

    private CreateUserProgressObject getPlayerObject(Player p) { return createUserMap.get(p); }

    public void addPlayer(Player p) {
        createUserMap.put(p, new CreateUserProgressObject());
        p.sendMessage(magmaPay.getLocalConfig().getMessageCreateUserEmail());
    }

    void removePlayer(Player p) {
        createUserMap.remove(p);
    }
}
