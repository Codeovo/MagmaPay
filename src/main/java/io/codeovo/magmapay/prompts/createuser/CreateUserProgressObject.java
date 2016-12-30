package io.codeovo.magmapay.prompts.createuser;

public class CreateUserProgressObject {
    private CreateUserStep userStep;
    private String email;
    private String pinHash;

    private String address;
    private String city;
    private String stateOrProvince;
    private String zip;
    private String country;

    private String cardName;
    private String cardNumber;
    private String cardMonth;
    private String cardYear;
    private String cardCVC;

    CreateUserProgressObject() {
        this.userStep = CreateUserStep.EMAIL;
    }

    CreateUserStep getUserStep() { return userStep; }

    void setUserStep(CreateUserStep userStep) {
        this.userStep = userStep;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getPinHash() {
        return pinHash;
    }

    void setPinHash(String pinHash) { this.pinHash = pinHash; }

    public String getAddress() { return address; }

    void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }

    void setCity(String city) { this.city = city; }

    public String getStateOrProvince() { return stateOrProvince; }

    void setStateOrProvince(String stateOrProvince) { this.stateOrProvince = stateOrProvince; }

    public String getZip() { return zip; }

    void setZip(String zip) { this.zip = zip; }

    public String getCountry() { return country; }

    void setCountry(String country) { this.country = country; }

    public String getCardName() { return cardName; }

    void setCardName(String cardName) { this.cardName = cardName; }

    public String getCardNumber() { return cardNumber; }

    void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardMonth() { return cardMonth; }

    void setCardMonth(String cardMonth) { this.cardMonth = cardMonth; }

    public String getCardYear() { return cardYear; }

    void setCardYear(String cardYear) { this.cardYear = cardYear; }

    public String getCardCVC() { return cardCVC; }

    void setCardCVC(String cardCVC) { this.cardCVC = cardCVC; }
}
