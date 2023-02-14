package com.artmart.models;

public class CustomProduct extends Product {
    private int customProductId;

    public CustomProduct() {
    }

    public CustomProduct(int customProductId, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.customProductId = customProductId;
    }
    
    
    public CustomProduct(int customProductId) {
        super();
        this.customProductId=customProductId;
    }

    public CustomProduct(int customProductId, int productId) {
        super(productId);
        this.customProductId = customProductId;
    }
   

    public int getCustomProductId() {
        return customProductId;
    }

    public void setCustomProductId(int customProductId) {
        this.customProductId = customProductId;
    }
    
    @Override
    public String toString() {
        return "CustomProduct{" + "customProductId=" + customProductId + '}';
    }
}
