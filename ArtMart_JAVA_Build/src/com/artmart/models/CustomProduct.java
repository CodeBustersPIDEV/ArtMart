package com.artmart.models;

public class CustomProduct extends Product {

    private int customProductId;

    public CustomProduct() {
    }

    public CustomProduct(Product p) {
        super(p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
    }

    public CustomProduct(int customid, Product p) {
        super(p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.customProductId = customid;
    }

    public CustomProduct(int aInt, int productId, Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CustomProduct(int productId, String name, String description, double weight) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CustomProduct(Product value, String text, String text0, double parseDouble, String text1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CustomProduct(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCustomProductId() {
        return customProductId;
    }

    public void setCustomProductId(int customProductId) {
        this.customProductId = customProductId;
    }

    @Override
    public String toString() {
        return "CustomProduct{" + "customProductId=" + customProductId + super.toString() + '}';
    }

    
}
