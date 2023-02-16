package com.artmart.models;

public class CustomProduct extends Product {
    private int customProductId;
    private Product product;

    public CustomProduct() {
    }
 public CustomProduct(Product p) {
        super(p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
     
    }
 
 public CustomProduct(int customid,Product p) {
        super(p.getCategoryId(), p.getName(), p.getDescription(), p.getDimensions(), p.getWeight(), p.getMaterial(), p.getImage());
     this.customProductId=customid;
    }

    public CustomProduct(int aInt, int productId, Product product) {
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
    return "CustomProduct{" +
           "customProductId=" + customProductId +
           "\nproductId=" + productId +
           "\nproduct=" + product.toString() +
  "\n*****************************************************" +
           '}';
}


}
