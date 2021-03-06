package io.codeovo.magmapay;

import io.codeovo.magmapay.api.MagmaPayAPI;
import io.codeovo.magmapay.cache.CacheManager;
import io.codeovo.magmapay.config.LocalConfig;
import io.codeovo.magmapay.listeners.PlayerListener;
import io.codeovo.magmapay.metrics.Metrics;
import io.codeovo.magmapay.payments.StripeImplementation;
import io.codeovo.magmapay.prompts.PromptManager;
import io.codeovo.magmapay.storage.Storage;

import io.codeovo.magmapay.webhooks.WebhookManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MagmaPay extends JavaPlugin {
    private static MagmaPay magmaPay;

    private static MagmaPayAPI magmaPayAPI;

    private CacheManager cacheManager;
    private LocalConfig localConfig;
    private Storage localStorage;

    private PromptManager promptManager;

    private StripeImplementation stripeImplementation;

    private WebhookManager webhookManager;

    @Override
    public void onEnable() {
        getLogger().info("MagmaPay - Enabling...");
        magmaPay = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        cacheManager = new CacheManager(this);
        localConfig = new LocalConfig(this);
        localStorage = new Storage(this);

        promptManager = new PromptManager(this);

        stripeImplementation = new StripeImplementation(this);
        magmaPayAPI = new MagmaPayAPI(this);

        if (localConfig.isUseWebHooks()) {
            webhookManager = new WebhookManager(this);
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ignored) {}

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

    public static MagmaPayAPI getMagmaPayAPI() { return magmaPayAPI; }

    public CacheManager getCacheManager() { return cacheManager; }

    public LocalConfig getLocalConfig() { return localConfig; }

    public Storage getLocalStorage() { return localStorage; }

    public PromptManager getPromptManager() { return promptManager; }

    public StripeImplementation getStripeImplementation() { return stripeImplementation; }

    public WebhookManager getWebhookManager() { return webhookManager; }
}
