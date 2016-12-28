package io.codeovo.magmapay.prompts.createuser;

public class CreateUserProgressObject {
    private CreateUserStep userStep;
    private String email;
    private String pinHash;

    public CreateUserProgressObject() {
        this.userStep = CreateUserStep.EMAIL;
    }

    public CreateUserStep getUserStep() { return userStep; }

    public void setUserStep(CreateUserStep userStep) {
        this.userStep = userStep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }
}
