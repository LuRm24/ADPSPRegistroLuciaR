package org.example.adpspregistroluciar.classes;

public class Address {
    private String street;
    private String number;
    private String postalCode;

    public Address(String number, String street, String postalCode) {
        this.number = number;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
