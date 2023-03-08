package com.artmart.models;

import java.sql.*;

public class Blog {

    private int id;
    private String title;
    private String content;
    private Date publishDate;
    private double rating;
    private int nb_views;
    private int author;

    public Blog() {
    }

    public Blog(String title, String content, int author) {
        this.title = title;
        this.content = content;
//        this.rating = rating;
//        this.nb_views = nb_views;
        this.author = author;
    }

    public Blog(String title, String content, Date publishDate, double rating, int nb_views, int author) {
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.rating = rating;
        this.nb_views = nb_views;
        this.author = author;
    }

    public Blog(int id, String title, String content, Date publishDate, double rating, int nb_views, int author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.rating = rating;
        this.nb_views = nb_views;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", title=" + title + ", content=" + content + ", publishDate=" + publishDate + ", rating=" + rating + ", nb_views=" + nb_views + ", author=" + author + '}';
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public int getAuthor() {
        return author;
    }

    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNb_views() {
        return nb_views;
    }

    public void setNb_views(int nb_views) {
        this.nb_views = nb_views;
    }

}
