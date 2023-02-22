package com.artmart.models;

public class ReadyProduct extends Product {

    private int readyProductId;
    private int price;

    public ReadyProduct() {
    }

    public ReadyProduct(int readyProductId) {
        this.readyProductId = readyProductId;
    }

    public ReadyProduct(Product p) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
    }
    
    public ReadyProduct(int aInt, int productId, Product product) {
    }

    public ReadyProduct(int readyProductId, Product p) {
        super(p.getProductId(), p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
        this.readyProductId = readyProductId;
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

    @Override
    public String toString() {
        return "ReadyProduct{" + "readyProductId=" + readyProductId + ", price=" + price + '}';
    }

}
