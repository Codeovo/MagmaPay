package io.codeovo.magmapay.prompts.createuser;

class CreateUserProgressObject {
    private CreateUserStep userStep;
    private String email;
    private String pinHash;

    private String address;
    private String city;
    private String stateOrProvince;
    private String zip;
    private String country;

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

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getPinHash() {
        return pinHash;
    }

    void setPinHash(String pinHash) { this.pinHash = pinHash; }

    String getAddress() { return address; }

    void setAddress(String address) { this.address = address; }

    String getCity() { return city; }

    void setCity(String city) { this.city = city; }

    String getStateOrProvince() { return stateOrProvince; }

    void setStateOrProvince(String stateOrProvince) { this.stateOrProvince = stateOrProvince; }

    String getZip() { return zip; }

    void setZip(String zip) { this.zip = zip; }

    String getCountry() { return country; }

    void setCountry(String country) { this.country = country; }

    String getCardNumber() { return cardNumber; }

    void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    String getCardMonth() { return cardMonth; }

    void setCardMonth(String cardMonth) { this.cardMonth = cardMonth; }

    String getCardYear() { return cardYear; }

    void setCardYear(String cardYear) { this.cardYear = cardYear; }

    String getCardCVC() { return cardCVC; }

    void setCardCVC(String cardCVC) { this.cardCVC = cardCVC; }
}
