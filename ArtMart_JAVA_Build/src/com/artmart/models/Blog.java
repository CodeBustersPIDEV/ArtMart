package com.artmart.models;

import java.sql.*;

public class Blog {

    private int id;
    private String title;
    private String content;
    private Date publishDate;
    private int author;

    public Blog() {
    }
public Blog(String title, String content, int author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    public Blog(String title, String content, Date publishDate, int author) {
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.author = author;
    }

    public Blog(int id, String title, String content, Date publishDate, int author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", title=" + title + ", content=" + content + ", publishDate=" + publishDate + ", author=" + author + '}';
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

}
