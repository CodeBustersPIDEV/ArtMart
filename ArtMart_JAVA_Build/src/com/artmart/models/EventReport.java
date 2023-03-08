package com.artmart.models;

public class EventReport {

    private int eventReportID;
    private int eventID;
    private int attendance;

    public EventReport() {
    }

    public EventReport(int eventReportID, int eventID, int attendance) {
        this.eventReportID = eventReportID;
        this.eventID = eventID;
        this.attendance = attendance;
    }

    public EventReport(int eventID, int attendance) {
        this.eventID = eventID;
        this.attendance = attendance;
    }

    public int getEventReportID() {
        return eventReportID;
    }

    public void setEventReportID(int eventReportID) {
        this.eventReportID = eventReportID;
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
        return "EventReport{" + "eventReportID=" + eventReportID + ", eventID=" + eventID + ", attendance=" + attendance + '}';
    }


}
