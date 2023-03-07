package com.artmart.models;

import java.util.Date;

public class Comment {

    private int id;
    private String content;
    private Date publishDate;
    private int author;
    private int blog_id;
    private int rating;

    public Comment() {
    }

    public Comment(int id, String content, Date publishDate, int rating,int author, int blog_id) {
        this.id = id;
        this.content = content;
        this.publishDate = publishDate;
        this.rating = rating;
        this.author = author;
        this.blog_id = blog_id;
    }

    public Comment(String content,  int rating,int author, int blog_id) {
        this.content = content;
        this.rating = rating;
        this.author = author;
        this.blog_id = blog_id;
    }

    public Comment(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content=" + content + ", publishDate=" + publishDate + ", rating=" + rating +", author=" + author + ", blog_id=" + blog_id + '}';
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

    public int getBlog_id() {
        return blog_id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    

}
