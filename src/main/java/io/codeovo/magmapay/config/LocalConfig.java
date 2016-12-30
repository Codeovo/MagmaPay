package io.codeovo.magmapay.config;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.storage.StorageType;
import io.codeovo.magmapay.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class LocalConfig {
    private MagmaPay magmaPay;
    private FileConfiguration config;

    private int configVersion;

    private boolean collectBillingAddress;

    private StorageType storageType;

    private String mysqlIP;
    private int mysqlPort;
    private String mysqlDatabase;
    private String mysqlUsername;
    private String mysqlPassword;

    private String sqliteUrl;

    private String stripeApiKey;

    private String messageCreateUserEmail;
    private String messageCreateUserPin;

    private String messageCreateUserAddress;
    private String messageCreateUserCity;
    private String messageCreateUserState;
    private String messageCreateUserZip;
    private String messageCreateUserCountry;

    private String messageCreateUserName;
    private String messageCreateUserCardNumber;
    private String messageCreateUserCardMonth;
    private String messageCreateUserCardYear;
    private String messageCreateUserCardCVC;

    private String messageCreateUserCreating;
    private String messageCreateUserCreated;
    private String messageCreateUserQuitSucessful;

    private String messageCreateUserValidationError;
    private String messageCreateUserStripeError;
    private String messageCreateUserEmailError;
    private String messageCreateUserPinError;

    private String messagePinRetrieveEnterPin;

    public LocalConfig(MagmaPay magmaPay) {
        magmaPay.saveDefaultConfig();

        this.magmaPay = magmaPay;
        this.config = magmaPay.getConfig();

        loadConfig();
    }

    private void loadConfig() {
        configVersion = config.getInt("config-version");

        collectBillingAddress = config.getBoolean("general.collect-billing-address");

        if (config.getBoolean("storage.mysql.use")) {
            storageType = StorageType.MYSQL;

            mysqlIP = config.getString("storage.mysql.ip");
            mysqlPort = config.getInt("storage.mysql.port");
            mysqlDatabase = config.getString("storage.mysql.database");
            mysqlUsername = config.getString("storage.mysql.username");
            mysqlPassword = config.getString("storage.mysql.password");
        }

        if (config.getBoolean("storage.sqlite.use")) {
            storageType = StorageType.SQLITE;

            sqliteUrl = config.getString("storage.sqlite.url");
        }

        stripeApiKey = config.getString("methods.stripe.api-key");

        messageCreateUserEmail = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-email"));
        messageCreateUserPin = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-pin"));

        messageCreateUserAddress = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-address"));
        messageCreateUserCity = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-city"));
        messageCreateUserState = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-state"));
        messageCreateUserZip = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-zip"));
        messageCreateUserCountry = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-country"));

        messageCreateUserName = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-card-name"));
        messageCreateUserCardNumber = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-card-number"));
        messageCreateUserCardMonth = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-card-month"));
        messageCreateUserCardYear = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-card-year"));
        messageCreateUserCardCVC = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.enter-card-cvc"));

        messageCreateUserCreating = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.creating-user"));
        messageCreateUserCreated = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.user-created"));
        messageCreateUserQuitSucessful = GeneralUtils
                .colour(config.getString("messages.create-user.prompts.quit-successful"));

        messageCreateUserValidationError = GeneralUtils
                .colour(config.getString("messages.create-user.errors.validation-error"));
        messageCreateUserStripeError = GeneralUtils
                .colour(config.getString("messages.create-user.errors.stripe-error"));
        messageCreateUserEmailError = GeneralUtils
                .colour(config.getString("messages.create-user.errors.invalid-email"));
        messageCreateUserPinError = GeneralUtils
                .colour(config.getString("messages.create-user.errors.invalid-pin"));

        messagePinRetrieveEnterPin = GeneralUtils
                .colour(config.getString("messages.pin-retrieval.prompts.enter-pin"));
    }

    public boolean isCollectBillingAddress() { return collectBillingAddress; }

    public StorageType getStorageType() { return storageType; }

    public String getMysqlIP() { return mysqlIP; }

    public int getMysqlPort() { return mysqlPort; }

    public String getMysqlDatabase() { return mysqlDatabase; }

    public String getMysqlUsername() { return mysqlUsername; }

    public String getMysqlPassword() { return mysqlPassword; }

    public String getSqliteUrl() { return sqliteUrl; }

    public String getStripeApiKey() { return stripeApiKey; }

    public String getMessageCreateUserEmail() { return messageCreateUserEmail; }

    public String getMessageCreateUserPin() { return messageCreateUserPin; }

    public String getMessageCreateUserAddress() { return messageCreateUserAddress; }

    public String getMessageCreateUserCity() { return messageCreateUserCity; }

    public String getMessageCreateUserState() { return messageCreateUserState; }

    public String getMessageCreateUserZip() { return messageCreateUserZip; }

    public String getMessageCreateUserCountry() { return messageCreateUserCountry; }

    public String getMessageCreateUserName() { return messageCreateUserName; }

    public String getMessageCreateUserCardNumber() { return messageCreateUserCardNumber; }

    public String getMessageCreateUserCardMonth() { return messageCreateUserCardMonth; }

    public String getMessageCreateUserCardYear() { return messageCreateUserCardYear; }

    public String getMessageCreateUserCardCVC() { return messageCreateUserCardCVC; }

    public String getMessageCreateUserCreating() { return messageCreateUserCreating; }

    public String getMessageCreateUserCreated() { return messageCreateUserCreated; }

    public String getMessageCreateUserQuitSucessful() { return messageCreateUserQuitSucessful; }

    public String getMessageCreateUserValidationError() { return messageCreateUserValidationError; }

    public String getMessageCreateUserStripeError() { return messageCreateUserStripeError; }

    public String getMessageCreateUserEmailError() { return messageCreateUserEmailError; }

    public String getMessageCreateUserPinError() { return messageCreateUserPinError; }

    public String getMessagePinRetrieveEnterPin() { return messagePinRetrieveEnterPin; }
}
