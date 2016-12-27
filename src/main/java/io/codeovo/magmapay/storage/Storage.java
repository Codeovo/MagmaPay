package io.codeovo.magmapay.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.LocalPlayer;
import io.codeovo.magmapay.utils.Encryption;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.UUID;

public class Storage {
    private MagmaPay magmaPay;
    private HikariDataSource pool;

    public Storage(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        connectDatabase();
        createTables();
    }

    private void connectDatabase() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("MagmaPayPool");
        hikariConfig.setMaximumPoolSize(50);

        if (magmaPay.getLocalConfig().getStorageType() == StorageType.MYSQL) {
            hikariConfig.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

            hikariConfig.addDataSourceProperty("serverName", magmaPay.getLocalConfig().getMysqlIP());
            hikariConfig.addDataSourceProperty("port", magmaPay.getLocalConfig().getMysqlPort());
            hikariConfig.addDataSourceProperty("databaseName", magmaPay.getLocalConfig().getMysqlDatabase());
            hikariConfig.addDataSourceProperty("user", magmaPay.getLocalConfig().getMysqlUsername());
            hikariConfig.addDataSourceProperty("password", magmaPay.getLocalConfig().getMysqlPassword());
        } else if (magmaPay.getLocalConfig().getStorageType() == StorageType.SQLITE) {
            hikariConfig.setDriverClassName("org.sqlite.JDBC");

            hikariConfig.setJdbcUrl(magmaPay.getLocalConfig().getSqliteUrl());
        }

        pool = new HikariDataSource(hikariConfig);
    }

    private void createTables() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = pool.getConnection();

            String statement = "CREATE TABLE IF NOT EXISTS mp_players(player_uuid VARCHAR(36) PRIMARY KEY, stripe_token VARCHAR(36), pin_hash VARCHAR(128))";
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.execute();
        } catch (Exception e) {
            Bukkit.getLogger().info("MagmaPay - Database connection failed, disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(MagmaPay.getInstance());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ignored) {}
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    public void addUser(final UUID playerUUID, final String stripeToken, final String pinToken) {
        Bukkit.getScheduler().runTaskAsynchronously(MagmaPay.getInstance(), new Runnable() {
            @Override
            public void run() {
                addUserLocal(playerUUID, stripeToken, Encryption.securePass(pinToken));
            }
        });
    }

    private void addUserLocal(UUID playerUUID, String stripeToken, String pinToken) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = pool.getConnection();

            preparedStatement = connection.prepareStatement("REPLACE INTO mp_players (player_uuid, stripe_token, pin_token) VALUES (?, ?, ?)");
            preparedStatement.setString(1, playerUUID.toString());
            preparedStatement.setString(2, stripeToken);
            preparedStatement.setString(3, pinToken);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ignored) {}
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    public LocalPlayer getPlayerData(final Player p) {
        final LocalPlayer localPlayer = new LocalPlayer();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = pool.getConnection();

            preparedStatement = connection.prepareStatement("SELECT stripe_token, pin_token FROM mp_players WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                localPlayer.setStripeToken(resultSet.getString(1));
                localPlayer.setPinHash(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ignored) {}
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) {}
            }
        }

        return localPlayer;
    }
}
