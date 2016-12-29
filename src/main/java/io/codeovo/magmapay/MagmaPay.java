package io.codeovo.magmapay;

import io.codeovo.magmapay.cache.CacheManager;
import io.codeovo.magmapay.config.LocalConfig;
import io.codeovo.magmapay.executors.ExecutorServiceManager;
import io.codeovo.magmapay.listeners.PlayerListener;
import io.codeovo.magmapay.payments.StripeImplementation;
import io.codeovo.magmapay.prompts.PromptManager;
import io.codeovo.magmapay.storage.Storage;
import io.codeovo.magmapay.test.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MagmaPay extends JavaPlugin {
    private static MagmaPay magmaPay;

    private CacheManager cacheManager;
    private LocalConfig localConfig;
    private Storage localStorage;

    private ExecutorServiceManager executorServiceManager;
    private PromptManager promptManager;

    private StripeImplementation stripeImplementation;

    @Override
    public void onEnable() {
        getLogger().info("MagmaPay - Enabling...");
        magmaPay = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        cacheManager = new CacheManager(this);
        localConfig = new LocalConfig(this);
        localStorage = new Storage(this);

        executorServiceManager = new ExecutorServiceManager(this);
        promptManager = new PromptManager(this);

        stripeImplementation = new StripeImplementation(this);

        // TEST IMPLEMENTATION
        getCommand("test").setExecutor(new TestCommand(this));

        getLogger().info("MagmaPay - Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MagmaPay - Disabling...");
        cacheManager.shutdown();
        executorServiceManager.shutdown();

        magmaPay = null;
        getLogger().info("MagmaPay - Disabled.");
    }

    public static MagmaPay getInstance() { return magmaPay; }

    public CacheManager getCacheManager() { return cacheManager; }

    public LocalConfig getLocalConfig() { return localConfig; }

    public Storage getLocalStorage() { return localStorage; }

    public PromptManager getPromptManager() { return promptManager; }

    public ExecutorServiceManager getExecutorServiceManager() { return executorServiceManager; }

    public StripeImplementation getStripeImplementation() { return stripeImplementation; }
}
