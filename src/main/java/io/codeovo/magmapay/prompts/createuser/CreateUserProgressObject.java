package io.codeovo.magmapay.prompts.createuser;

class CreateUserProgressObject {
    private CreateUserStep userStep;
    private String email;
    private String pinHash;

    CreateUserProgressObject() {
        this.userStep = CreateUserStep.EMAIL;
    }

    CreateUserStep getUserStep() { return userStep; }

    void setUserStep(CreateUserStep userStep) {
        this.userStep = userStep;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getPinHash() {
        return pinHash;
    }

    void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }
}
