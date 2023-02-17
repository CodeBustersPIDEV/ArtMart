package com.artmart.models;

public class ReadyProduct extends Product {

    private int readyProductId;
    private int price;
    private Product product;

    public ReadyProduct(int readyProductId, int price, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.readyProductId = readyProductId;
        this.price = price;
    }

    public ReadyProduct(int price, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.price = price;
    }

    public ReadyProduct(int readyProductId, int price, Product product) {
        this.readyProductId = readyProductId;
        this.price = price;
        this.product = product;
    }

    public ReadyProduct(int readyProductId, int price, Product product, int productId) {
        super(productId);
        this.readyProductId = readyProductId;
        this.price = price;
        this.product = product;
    }

    public ReadyProduct(int readyProductId) {
        super();
        this.readyProductId = readyProductId;
    }

    public ReadyProduct(int readyProductId, int productId) {
        super(productId);
        this.readyProductId = readyProductId;
    }

    public ReadyProduct(Product product, int productId) {
        super(productId);
        this.product = product;
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
