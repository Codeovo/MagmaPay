package io.codeovo.magmapay.executors;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.utils.Encryption;

import java.util.concurrent.*;

public class ExecutorServiceManager {
    private MagmaPay magmaPay;
    private ExecutorService executorService;

    public ExecutorServiceManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.executorService = Executors.newCachedThreadPool();
    }

    public String getHashedPin(final String pin) {
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Encryption.securePass(pin);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
