package io.codeovo.magmapay.prompts;

import io.codeovo.magmapay.MagmaPay;
import io.codeovo.magmapay.prompts.createuser.CreateUserManager;

public class PromptManager {
    private MagmaPay magmaPay;
    private CreateUserManager createUserManager;

    public PromptManager(MagmaPay magmaPay) {
        this.magmaPay = magmaPay;

        createUserManager = new CreateUserManager(magmaPay);
    }

    public CreateUserManager getCreateUserManager() { return createUserManager; }
}
