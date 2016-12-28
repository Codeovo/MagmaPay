package io.codeovo.magmapay.prompts;

import io.codeovo.magmapay.prompts.createuser.CreateUserManager;

public class PromptManager {
    private CreateUserManager createUserManager;

    public PromptManager() {
        createUserManager = new CreateUserManager();
    }

    public CreateUserManager getCreateUserManager() { return createUserManager; }
}
