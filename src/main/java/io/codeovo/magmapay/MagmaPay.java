package io.codeovo.magmapay;

import io.codeovo.magmapay.cache.CacheManager;
import io.codeovo.magmapay.config.LocalConfig;
import io.codeovo.magmapay.listeners.PlayerListener;
import io.codeovo.magmapay.storage.Storage;
import org.bukkit.plugin.java.JavaPlugin;

public class MagmaPay extends JavaPlugin {
    private static MagmaPay magmaPay;

    private CacheManager cacheManager;
    private LocalConfig localConfig;
    private Storage localStorage;

    @Override
    public void onEnable() {
        getLogger().info("MagmaPay - Enabling...");
        magmaPay = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        cacheManager = new CacheManager(this);
        localConfig = new LocalConfig(this);
        localStorage = new Storage(this);

        getLogger().info("MagmaPay - Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MagmaPay - Disabling...");
        cacheManager.shutdown();

        magmaPay = null;
        getLogger().info("MagmaPay - Disabled.");
    }

    public static MagmaPay getInstance() { return magmaPay; }

    public CacheManager getCacheManager() { return cacheManager; }

    public LocalConfig getLocalConfig() { return localConfig; }

    public Storage getLocalStorage() { return localStorage; }
}