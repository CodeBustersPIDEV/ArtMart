/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.models;

/**
 *
 * @author marwen
 */
public class HasCategory {
    private int id;
    private int blog_id;
    private int category_id;

    public HasCategory(int id, int blog_id, int category_id) {
        this.id = id;
        this.blog_id = blog_id;
        this.category_id = category_id;
    }

    public HasCategory(int blog_id, int category_id) {
        this.blog_id = blog_id;
        this.category_id = category_id;
    }

    public HasCategory(int category_id) {
        this.category_id = category_id;
    }

    
    public int getId() {
        return id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "HasCategory{" + "id=" + id + ", blog_id=" + blog_id + ", category_id=" + category_id + '}';
    }
    
    
}
