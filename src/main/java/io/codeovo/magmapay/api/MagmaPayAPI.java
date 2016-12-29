package io.codeovo.magmapay.api;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.objects.charges.ChargeRequest;
import io.codeovo.magmapay.objects.charges.ChargeResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MagmaPayAPI {
    private MagmaPay magmaPay;
    private ExecutorService executorService;

    public MagmaPayAPI(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;
        this.executorService = Executors.newCachedThreadPool();
    }

    public ChargeResponse chargePlayer(ChargeRequest chargeRequest) {


        Future<ChargeResponse> future = executorService.submit(new Callable<ChargeResponse>() {
            @Override
            public ChargeResponse call() throws Exception {

            }
        });


    }
}
