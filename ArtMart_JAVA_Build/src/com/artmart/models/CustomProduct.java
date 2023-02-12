package com.artmart.models;
import com.artmart.models.Product;


public class CustomProduct extends Product {
    private int customProductId;

    public CustomProduct(int customProductId, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.customProductId = customProductId;
    }

    public CustomProduct() {
    }

   

    public CustomProduct(int aInt, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void CustomProduct(int aInt, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCustomProductId() {
        return customProductId;
    }

    public void setCustomProductId(int customProductId) {
        this.customProductId = customProductId;
    }
}
