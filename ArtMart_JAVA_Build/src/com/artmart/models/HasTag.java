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
public class HasTag {
    private int id;
    private int blog_id;
    private int tag_id;

    public HasTag(int id, int blog_id, int tag_id) {
        this.id = id;
        this.blog_id = blog_id;
        this.tag_id = tag_id;
    }

    public HasTag(int blog_id, int category_id) {
        this.blog_id = blog_id;
        this.tag_id = tag_id;
    }

    public HasTag(int category_id) {
        this.tag_id = tag_id;
    }

    
    public int getId() {
        return id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    @Override
    public String toString() {
        return "HasTag{" + "id=" + id + ", blog_id=" + blog_id + ", tag_id=" + tag_id + '}';
    }
    
}
