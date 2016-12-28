package io.codeovo.magmapay.prompts.createuser;

public enum CreateUserStep {
    EMAIL,
    PIN;

    private static CreateUserStep[] vals = values();
    public CreateUserStep next() {
        return vals[(this.ordinal()+1) % vals.length];
    }
}
