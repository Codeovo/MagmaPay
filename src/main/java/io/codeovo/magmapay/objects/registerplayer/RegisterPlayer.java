package io.codeovo.magmapay.objects.registerplayer;

import io.codeovo.magmapay.utils.Encryption;

public class RegisterPlayer {
    private String email;
    private String pin;

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

    public RegisterPlayer(String email, String pin, String address, String city, String stateOrProvince,
                          String zip, String country, String cardName, String cardNumber, String cardMonth,
                          String cardYear, String cardCVC) {
        this.email = email;
        this.pin = Encryption.securePass(pin);

        this.address = address;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.zip = zip;
        this.country = country;

        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardCVC = cardCVC;
    }

    public RegisterPlayer(String email, String pin, String cardName, String cardNumber,
                          String cardMonth, String cardYear, String cardCVC) {
        this.email = email;
        this.pin = Encryption.securePass(pin);

        this.address = null;

        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardCVC = cardCVC;
    }

    public String getEmail() { return email; }

    public String getPin() { return pin; }

    public String getAddress() { return address; }

    public String getCity() { return city; }

    public String getStateOrProvince() { return stateOrProvince; }

    public String getZip() { return zip; }

    public String getCountry() { return country; }

    public String getCardName() { return cardName; }

    public String getCardNumber() { return cardNumber; }

    public String getCardMonth() { return cardMonth; }

    public String getCardYear() { return cardYear; }

    public String getCardCVC() { return cardCVC; }
}
