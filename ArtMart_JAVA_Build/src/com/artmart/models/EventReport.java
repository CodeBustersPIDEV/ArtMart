/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.models;

/**
 *
 * @author GhassenZ
 */
public class EventReport {
    private int reportID;
    private int eventID;
    private int attendance;

    public EventReport() {
    }

    public EventReport(int reportID, int eventID, int attendance) {
        this.reportID = reportID;
        this.eventID = eventID;
        this.attendance = attendance;
    }

    public EventReport(int eventID, int attendance) {
        this.eventID = eventID;
        this.attendance = attendance;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "EventReport{" + "reportID=" + reportID + ", eventID=" + eventID + ", attendance=" + attendance + '}';
    }
}
