package com.artmart.models;

import java.sql.*;

public class Feedback {

    private int feedbackID;
    private int eventReportID;
    private int rating;
    private String comment;
    private Date date;

    public Feedback() {
    }

    public Feedback(int feedbackID, int eventReportID, int rating, String comment, Date date) {
        this.feedbackID = feedbackID;
        this.eventReportID = eventReportID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Feedback(int eventReportID, int rating, String comment, Date date) {
        this.eventReportID = eventReportID;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getEventReportID() {
        return eventReportID;
    }

    public void setEventReportID(int eventReportID) {
        this.eventReportID = eventReportID;
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
        return "Feedback{" + "feedbackID=" + feedbackID + ", eventReportID=" + eventReportID + ", rating=" + rating + ", comment=" + comment + ", date=" + date + '}';
    }
}
