package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IEventReportDao;
import com.artmart.models.Activity;
import com.artmart.models.EventReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventReportDao implements IEventReportDao {

    private Connection connection;

    public EventReportDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createEventReport(EventReport eventReport) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO eventreport (eventID, userID, attendance) VALUES (?, ?, ?)"
            );
            statement.setInt(1, eventReport.getEventID());
            statement.setInt(2, eventReport.getUserID());
            statement.setInt(3, eventReport.getAttendance());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return result;
    }

    @Override
    public EventReport getEventReport(int eventReportID) {
        EventReport eventReport = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM EventReport WHERE reportID = ?"
            );
            statement.setInt(1, eventReportID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                eventReport = new EventReport();
                eventReport.setEventReportID(resultSet.getInt("reportID"));
                eventReport.setEventID(resultSet.getInt("eventID"));
                eventReport.setEventID(resultSet.getInt("userID"));
                eventReport.setAttendance(resultSet.getInt("attendance"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return eventReport;
    }

    @Override
    public List<EventReport> getAllEventReports() {
        List<EventReport> eventReports = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM EventReport"
            );
            while (resultSet.next()) {
                EventReport eventReport = new EventReport();
                eventReport.setEventReportID(resultSet.getInt("reportID"));
                eventReport.setEventID(resultSet.getInt("eventID"));
                eventReport.setEventID(resultSet.getInt("userID"));
                eventReport.setAttendance(resultSet.getInt("attendance"));

                eventReports.add(eventReport);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return eventReports;
    }

    @Override
    public boolean updateEventReport(int eventReportID,EventReport eventReport) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE EventReport "
                    + "SET eventID = ?, userID = ?, attendance = ? "
                    + "WHERE reportID = ?"
            );
            statement.setInt(1, eventReport.getEventID());
            statement.setInt(2, eventReport.getUserID());
            statement.setInt(3, eventReport.getAttendance());
            statement.setInt(4, eventReport.getEventReportID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEventReport(int eventReportID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM EventReport"
                    + " WHERE reportID = ?"
            );
            statement.setInt(1, eventReportID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public List<EventReport> getAllReportsByID(int userID) {
        List<EventReport> reports = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM eventreport a JOIN event e ON a.eventID = e.eventID WHERE e.userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                EventReport report = new EventReport();
                report.setEventReportID(resultSet.getInt("reportID"));
                report.setEventID(resultSet.getInt("eventID"));
                report.setAttendance(resultSet.getInt("attendance"));
                reports.add(report);
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return reports;
    }
}
