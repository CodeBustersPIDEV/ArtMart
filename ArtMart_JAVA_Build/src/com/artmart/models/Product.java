package com.artmart.models;

public class Product {

    int productId;
    private int categoryId;
    private String name;
    private String description;
    private String dimensions;
    private float weight;
    private String material;
    private String image;

    public Product(int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.weight = weight;
        this.material = material;
        this.image = image;
    }

    public Product(int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.weight = weight;
        this.material = material;
        this.image = image;
    }

    public Product() {
    }

    public Product(int productId) {
        this.productId = productId;
    }

    public Product(int aInt, String string, String string0, double aDouble, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Product(int aInt, int aInt0, int aInt1, String string, String string0, String string1, int aInt2, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getProductDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getProductPrice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getProductQuantity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Product{"
                + "productId=" + productId
                + ", categoryId=" + categoryId
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", dimensions='" + dimensions + '\''
                + ", weight=" + weight
                + ", material='" + material + '\''
                + ", image='" + image + '\''
                + '}';
    }

}
