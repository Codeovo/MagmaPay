package io.codeovo.magmapay.objects.charges;

public enum EarlyFailStatus {
    PLAYER_OFFLINE,
    COLLECTING_DATA_FROM_PREVIOUS_CHARGE,
    DATA_RETRIEVAL_ERROR,
    FAIL_DURING_DATA_RETRIEVAL,
    INCORRECT_PIN
}