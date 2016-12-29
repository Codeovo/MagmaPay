package io.codeovo.magmapay.executors;

import io.codeovo.magmapay.MagmaPay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceManager {
    private MagmaPay magmaPay;
    private ExecutorService executorService;

    public ExecutorServiceManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
