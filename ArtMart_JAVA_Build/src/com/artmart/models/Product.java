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
    private int userId;

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

    public Product(int id, int categoryId, String name, String description, String dimensions, int weight, String material, String image) {
        this.productId = id;
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

    public Product(int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image, int userId) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.weight = weight;
        this.material = material;
        this.image = image;
        this.userId = userId;
    }

    public Product(int id, int categoryId, String name, String description, String dimensions, int weight, String material, String image, int userId) {
        this.productId = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.weight = weight;
        this.material = material;
        this.image = image;
        this.userId = userId;
    }

    public Product(int categoryId, String name, String description, String dimensions, float weight, String material, String image, int userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.weight = weight;
        this.material = material;
        this.image = image;
        this.userId = userId;
    }

    public Product() {
    }

    public Product(int productId) {
        this.productId = productId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", categoryId=" + categoryId + ", name=" + name + ", description=" + description + ", dimensions=" + dimensions + ", weight=" + weight + ", material=" + material + ", image=" + image + ", userId=" + userId + '}';
    }

}
