package com.artmart.models;

public class ReadyProduct extends Product {

    private int readyProductId;
    private int userId;
    private int price;

    public ReadyProduct() {
    }

    public ReadyProduct(int readyProductId) {
        this.readyProductId = readyProductId;
    }

    public ReadyProduct(int readyProductId, int price) {
        this.readyProductId = readyProductId;
        this.price = price;
    }
    
    
    public ReadyProduct(int readyProductId, int productId, Product p, int price) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.readyProductId = readyProductId;
        this.price = price;
    }

    public ReadyProduct(int readyProductId, Product p, int price) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.readyProductId = readyProductId;
        this.price = price;
    }

    public ReadyProduct(int readyProductId, Product p, int price, int userId) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.readyProductId = readyProductId;
        this.price = price;
        this.userId = userId;
    }

    public ReadyProduct(Product p) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
    }

    public ReadyProduct(int aInt, int productId, Product product) {
    }

    public ReadyProduct(Product p, int price, int userId) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.price = price;
        this.userId = userId;
    }

    public ReadyProduct(Product p, int price) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.price = price;
    }

    public ReadyProduct(int readyProductId, Product p) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.readyProductId = readyProductId;
    }

    public ReadyProduct(int categoryId, String name, String description, String dimensions, float weight, String material, String image, int price, int userId) {
        super(categoryId, name, description, dimensions, weight, material, image);
        this.price = price;
        this.userId = userId;
    }

    public ReadyProduct(int categoryId, String name, String description, String dimensions, float weight, String material, String image, int price) {
        super(categoryId, name, description, dimensions, weight, material, image);
        this.price = price;
    }

    public int getReadyProductId() {
        return readyProductId;
    }

    public void setReadyProductId(int readyProductId) {
        this.readyProductId = readyProductId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReadyProduct{" + "readyProductId=" + readyProductId + ", price=" + price + ", userId=" + userId + '}';
    }

}
