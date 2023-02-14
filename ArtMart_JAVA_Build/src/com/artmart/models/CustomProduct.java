package com.artmart.models;

public class CustomProduct extends Product {
    private int customProductId;
    private static int nextCustomProductId = 1;
    private static int nextProductId = 1;
    private Product product;

    public CustomProduct() {
    }
 public CustomProduct(int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(nextProductId++, categoryId, name, description, dimensions, weight, material, image);
        this.customProductId = nextCustomProductId++;
    }
    public CustomProduct(int customProductId, int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
        this.customProductId = customProductId;
    }

    public CustomProduct(int productId, int categoryId, String name, String description, String dimensions, float weight, String material, String image) {
        super(productId, categoryId, name, description, dimensions, weight, material, image);
    }

   
    
    
    public CustomProduct(int customProductId) {
        super();
        this.customProductId=customProductId;
    }

    public CustomProduct(int customProductId, int productId) {
        super(productId);
        this.customProductId = customProductId;
    }

    public CustomProduct(int aInt, Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CustomProduct(int aInt, int productId, Product product) {
      this.customProductId = aInt;
    this.productId = productId;
    this.product = product;
    }

   

    public int getCustomProductId() {
        return customProductId;
    }

    public void setCustomProductId(int customProductId) {
        this.customProductId = customProductId;
    }
    
    @Override
public String toString() {
    return "CustomProduct{" +
           "customProductId=" + customProductId +
           "\nproductId=" + productId +
           "\nproduct=" + product.toString() +
  "\n*****************************************************" +
           '}';
}


}
