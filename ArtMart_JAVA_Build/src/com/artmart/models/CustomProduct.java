package com.artmart.models;

public class CustomProduct extends Product {

    private int customProductId;
    private int clientID;
private Product product;


    public Product getProduct() {
        return product;
    }
    public CustomProduct() {
    }

    public CustomProduct(Product p) {
        super(p.getProductId(),p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
    }

    public CustomProduct(int customid, Product p) {
        super(p.getProductId(),p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.customProductId = customid;
    }



     public CustomProduct(int customid,int clientID, Product p) {
        super(p.getProductId(),p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.customProductId = customid;
           this.clientID = clientID;
    }

    public CustomProduct(int clientID, Product product, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.clientID = clientID;
        this.product = product;
    }

    public CustomProduct(int clientID, Product product, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(categoryId, name, description, dimensions, weight, material, image);
        this.clientID = clientID;
        this.product = product;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public CustomProduct(int customProductId) {
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
        return "CustomProduct{" + "customProductId=" + customProductId + super.toString() + '}';
    }

    
}
