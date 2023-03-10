/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.models;

import java.sql.Date;

/**
 *
 * @author GhassenZ
 */
public class Participation {
    private int participationlD;
    private int userID;
    private int eventlD;
    private String attendanceStatus;    
    private Date registrationDate;

    public Participation() {
    }

    public Participation(int participationlD, int userID, int eventlD, String attendanceStatus, Date registrationDate) {
        this.participationlD = participationlD;
        this.userID = userID;
        this.eventlD = eventlD;
        this.attendanceStatus = attendanceStatus;
        this.registrationDate = registrationDate;
    }

    public Participation(int userID, int eventlD, String attendanceStatus, Date registrationDate) {
        this.userID = userID;
        this.eventlD = eventlD;
        this.attendanceStatus = attendanceStatus;
        this.registrationDate = registrationDate;
    }

    public Participation(int userID, int eventlD) {
        this.userID = userID;
        this.eventlD = eventlD;
    }

    public Participation(String attendanceStatus, Date registrationDate) {
        this.attendanceStatus = attendanceStatus;
        this.registrationDate = registrationDate;
    }

    public Participation(int userID, int eventlD, String attendanceStatus) {
        this.userID = userID;
        this.eventlD = eventlD;
        this.attendanceStatus = attendanceStatus;
    }
    

    public int getParticipationlD() {
        return participationlD;
    }

    public void setParticipationlD(int participationlD) {
        this.participationlD = participationlD;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventlD() {
        return eventlD;
    }

    public void setEventlD(int eventlD) {
        this.eventlD = eventlD;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Participation{" + "participationlD=" + participationlD + ", userID=" + userID + ", eventlD=" + eventlD + ", attendanceStatus=" + attendanceStatus + ", registrationDate=" + registrationDate + '}';
    }
    
}
