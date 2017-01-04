package io.codeovo.magmapay.objects.charges;

import com.stripe.model.Charge;

public class ChargeDataResponse {
    private EarlyFailStatus earlyFailStatus;
    private Charge charge;

    public ChargeDataResponse(EarlyFailStatus earlyFailStatus) {
        this.earlyFailStatus = earlyFailStatus;
        this.charge = null;
    }

    public ChargeDataResponse(Charge charge) {
        this.earlyFailStatus = null;
        this.charge = charge;
    }
}
