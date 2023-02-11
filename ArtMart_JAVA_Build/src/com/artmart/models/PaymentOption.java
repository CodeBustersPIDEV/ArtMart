package com.artmart.models;

public class PaymentOption {
    private int id;
    private String name;
    private String availableCountries;

    public PaymentOption() {
    }

    public PaymentOption(int id, String name, String availableCountries) {
        this.id = id;
        this.name = name;
        this.availableCountries = availableCountries;
    }

    public PaymentOption(String name, String availableCountries) {
        this.name = name;
        this.availableCountries = availableCountries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailableCountries() {
        return availableCountries;
    }

    public void setAvailableCountries(String availableCountries) {
        this.availableCountries = availableCountries;
    }

    @Override
    public String toString() {
        return "PaymentOption{" + "id=" + id + ", name=" + name + ", availableCountries=" + availableCountries + '}';
    }
    
    
}
