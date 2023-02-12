package com.artmart.models;

public class CustomProduct {
    private int productID;
    private int customProductID;

    public CustomProduct(int productID, int customProductID) {
        this.productID = productID;
        this.customProductID = customProductID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCustomProductID() {
        return customProductID;
    }

    public void setCustomProductID(int customProductID) {
        this.customProductID = customProductID;
    }
}
