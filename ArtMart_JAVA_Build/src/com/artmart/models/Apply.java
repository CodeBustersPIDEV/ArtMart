
package com.artmart.models;


public class Apply {
       int apply_ID;
int customproduct_ID;
int artist_ID;
    private String status;

    public Apply(int apply_ID, int customproduct_ID, int artist_ID, String status) {
        this.apply_ID = apply_ID;
        this.customproduct_ID = customproduct_ID;
        this.artist_ID = artist_ID;
        this.status = status;
    }

    public Apply(int customproduct_ID, int artist_ID, String status) {
        this.customproduct_ID = customproduct_ID;
        this.artist_ID = artist_ID;
        this.status = status;
    }

    public Apply() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getApply_ID() {
        return apply_ID;
    }

    public int getCustomproduct_ID() {
        return customproduct_ID;
    }

    public int getArtist_ID() {
        return artist_ID;
    }

    public String getStatus() {
        return status;
    }

    public void setApply_ID(int apply_ID) {
        this.apply_ID = apply_ID;
    }

    public void setCustomproduct_ID(int customproduct_ID) {
        this.customproduct_ID = customproduct_ID;
    }

    public void setArtist_ID(int artist_ID) {
        this.artist_ID = artist_ID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apply{" + "apply_ID=" + apply_ID + ", customproduct_ID=" + customproduct_ID + ", artist_ID=" + artist_ID + ", status=" + status + '}';
    }
    
    
    
}
