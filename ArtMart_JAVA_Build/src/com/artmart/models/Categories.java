package com.artmart.models;

public class Categories {

    int categories_ID;

    private String name;

    public int getCategories_ID() {
        return categories_ID;
    }

    public String getName() {
        return name;
    }

    public void setCategories_ID(int categories_ID) {
        this.categories_ID = categories_ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public Categories(int categories_ID, String name) {
        this.categories_ID = categories_ID;
        this.name = name;
    }

    public Categories(int categories_ID) {
        this.categories_ID = categories_ID;
    }

    @Override
    public String toString() {
        return "Categories [categories_ID=" + categories_ID + "\n name=" + name + "\n********************************************" + "]";
    }

    public int getname() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
