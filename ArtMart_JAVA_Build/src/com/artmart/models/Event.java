package com.artmart.models;

import java.sql.*;

public class Event {

    private int eventID;
    private String name;
    private String location;
    private String type;
    private String description;
    private double entryFee;
    private int capacity;
    private Date startDate;
    private Date endDate;
    private int userID;

    public Event() {
    }

    public Event(int eventID, String name, String location, String type, String description, double entryFee, int capacity, Date startDate, Date endDate, int userID) {
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.type = type;
        this.description = description;
        this.entryFee = entryFee;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userID = userID;
    }

    public Event(String name, String location, String type, String description, double entryFee, int capacity, Date startDate, Date endDate, int userID) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.description = description;
        this.entryFee = entryFee;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userID = userID;
    }

    public Event(String name, String location, String type, String description, double entryFee, int capacity, Date startDate, Date endDate) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.description = description;
        this.entryFee = entryFee;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return name;
    }
}
