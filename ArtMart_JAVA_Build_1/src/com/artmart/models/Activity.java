package com.artmart.models;

import java.sql.*;

public class Activity {

    private int activityID;
    private int eventID;
    private Date date;
    private String title;
    private String host;

    public Activity() {
    }

    public Activity(int activityID, int eventID, Date date, String title, String host) {
        this.activityID = activityID;
        this.eventID = eventID;
        this.date = date;
        this.title = title;
        this.host = host;
    }

    public Activity(int eventID, Date date, String title, String host) {
        this.eventID = eventID;
        this.date = date;
        this.title = title;
        this.host = host;
    }

    public Activity(Date date, String title, String host) {
        this.date = date;
        this.title = title;
        this.host = host;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Activity{" + "activityID=" + activityID + ", eventID=" + eventID + ", startDate=" + date + ", title=" + title + ", host=" + host + '}';
    }
}
