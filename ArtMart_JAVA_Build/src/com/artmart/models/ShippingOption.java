package com.artmart.models;

public class ShippingOption {

    private int id;
    private String name;
    private String carrier;
    private String shippingSpeed;
    private double shippingFee;
    private String availableRegions;

    public ShippingOption() {
    }

    public ShippingOption(int id, String name, String carrier, String shippingSpeed, double shippingFee, String availableRegions) {
        this.id = id;
        this.name = name;
        this.carrier = carrier;
        this.shippingSpeed = shippingSpeed;
        this.shippingFee = shippingFee;
        this.availableRegions = availableRegions;
    }

    public ShippingOption(String name, String carrier, String shippingSpeed, double shippingFee, String availableRegions) {
        this.name = name;
        this.carrier = carrier;
        this.shippingSpeed = shippingSpeed;
        this.shippingFee = shippingFee;
        this.availableRegions = availableRegions;
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

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getShippingSpeed() {
        return shippingSpeed;
    }

    public void setShippingSpeed(String shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getAvailableRegions() {
        return availableRegions;
    }

    public void setAvailableRegions(String availableRegions) {
        this.availableRegions = availableRegions;
    }

    @Override
    public String toString() {
        return "ShippingOption{" + "id=" + id + ", name=" + name + ", carrier=" + carrier + ", shippingSpeed=" + shippingSpeed + ", shippingFee=" + shippingFee + ", availableRegions=" + availableRegions + '}';
    }

    public int getMinDeliveryTimeInDays() {
        // Get the minimum delivery time from the shipping speed string
        String[] parts = this.shippingSpeed.split("-");
        int minDeliveryTime = Integer.parseInt(parts[0].trim());
        return minDeliveryTime;
    }

    public int getMaxDeliveryTimeInDays() {
        // Get the maximum delivery time from the shipping speed string
        String[] parts = this.shippingSpeed.split("-");
        int maxDeliveryTime = Integer.parseInt(parts[1].trim().split(" ")[0]);
        return maxDeliveryTime;
    }
}
