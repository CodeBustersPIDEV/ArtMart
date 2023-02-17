package com.artmart.models;

import java.sql.*;

public class Activity {

    private int activityID;
    private int eventID;
    private Date startDate;
    private Date endDate;
    private String title;
    private String host;

    public Activity() {
    }

    public Activity(int activityID, int eventID, Date startDate, Date endDate, String title, String host) {
        this.activityID = activityID;
        this.eventID = eventID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.host = host;
    }

    public Activity(int eventID, Date startDate, Date endDate, String title, String host) {
        this.eventID = eventID;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        return "Activity{" + "activityID=" + activityID + ", eventID=" + eventID + ", startDate=" + startDate + ", endDate=" + endDate + ", title=" + title + ", host=" + host + '}';
    }
}
