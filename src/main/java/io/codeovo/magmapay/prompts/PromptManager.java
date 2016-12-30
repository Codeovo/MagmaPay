package io.codeovo.magmapay.prompts;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.prompts.createuser.CreateUserManager;
import io.codeovo.magmapay.prompts.pinretrieval.PinRetrievalManager;

public class PromptManager {
    private MagmaPay magmaPay;

    private CreateUserManager createUserManager;
    private PinRetrievalManager pinRetrievalManager;

    public PromptManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        createUserManager = new CreateUserManager(magmaPay);
        pinRetrievalManager = new PinRetrievalManager(magmaPay);
    }

    public CreateUserManager getCreateUserManager() { return createUserManager; }

    public PinRetrievalManager getPinRetrievalManager() { return pinRetrievalManager; }
}
