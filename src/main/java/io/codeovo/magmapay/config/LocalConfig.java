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

    private StorageType storageType;

    private String mysqlIP;
    private int mysqlPort;
    private String mysqlDatabase;
    private String mysqlUsername;
    private String mysqlPassword;

    private String sqliteUrl;

    private String messageCreateUserEmail;
    private String messageCreateUserPin;

    public LocalConfig(MagmaPay magmaPay) {
        magmaPay.saveDefaultConfig();

        this.magmaPay = magmaPay;
        this.config = magmaPay.getConfig();

        loadConfig();
    }

    private void loadConfig() {
        configVersion = config.getInt("config-version");

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

        messageCreateUserEmail = GeneralUtils.colour(config.getString("messages.create-user.enter-email"));
        messageCreateUserPin = GeneralUtils.colour(config.getString("messages.create-user.enter-pin"));
    }

    public StorageType getStorageType() { return storageType; }

    public String getMysqlIP() { return mysqlIP; }

    public int getMysqlPort() { return mysqlPort; }

    public String getMysqlDatabase() { return mysqlDatabase; }

    public String getMysqlUsername() { return mysqlUsername; }

    public String getMysqlPassword() { return mysqlPassword; }

    public String getSqliteUrl() { return sqliteUrl; }

    public String getMessageCreateUserEmail() { return messageCreateUserEmail; }

    public String getMessageCreateUserPin() { return messageCreateUserPin; }
}
