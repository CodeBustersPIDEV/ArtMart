/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.models;

import java.util.Date;

/**
 *
 * @author rymae
 */
public class ProductReview {

    private int reviewId;
    private int readyProductId;
    private String username;
    private String title;
    private String text;
    private int rating;
    private Date date;

    public ProductReview(int readyProductId, String username, String title, String text, int rating, Date date) {
        this.readyProductId = readyProductId;
        this.username = username;
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public ProductReview(int reviewId, int readyProductId, String username, String title, String text, int rating, Date date) {
        this.reviewId = reviewId;
        this.readyProductId = readyProductId;
        this.username = username;
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getReadyProductId() {
        return readyProductId;
    }

    public void setReadyProductId(int readyProductId) {
        this.readyProductId = readyProductId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductReview{" + "reviewId=" + reviewId + ", readyProductId=" + readyProductId + ", username=" + username + ", title=" + title + ", text=" + text + ", rating=" + rating + ", date=" + date + '}';
    }

}
