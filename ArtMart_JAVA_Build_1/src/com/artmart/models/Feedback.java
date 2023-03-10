package com.artmart.models;

import java.sql.*;

public class Feedback {

    private int feedbackID;
    private int eventID;
    private int userID;
    private int rating;
    private String comment;
    private Date date;

    public Feedback() {
    }

    public Feedback(int feedbackID, int eventID, int userID, int rating, String comment, Date date) {
        this.feedbackID = feedbackID;
        this.eventID = eventID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Feedback(int eventID, int userID, int rating, String comment, Date date) {
        this.eventID = eventID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Feedback(int eventID, int userID, int rating, String comment) {
        this.eventID = eventID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedbackID=" + feedbackID + ", eventID=" + eventID + ", userID=" + userID + ", rating=" + rating + ", comment=" + comment + ", date=" + date + '}';
    }
}
