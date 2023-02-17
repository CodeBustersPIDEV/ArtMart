package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IEventDao;
import com.artmart.models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDao implements IEventDao {

    private Connection connection;

    public EventDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createEvent(Event event) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO event (userID, name, location, type, description, entryFee, capacity, startDate, endDate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, event.getUserID());
            statement.setString(2, event.getName());
            statement.setString(3, event.getLocation());
            statement.setString(4, event.getType());
            statement.setString(5, event.getDescription());
            statement.setDouble(6, event.getEntryFee());
            statement.setInt(7, event.getCapacity());
            statement.setDate(8, event.getStartDate());
            statement.setDate(9, event.getEndDate());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return result;
    }

    @Override
    public Event getEvent(int eventID) {
        Event event = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Event WHERE eventID = ?"
            );
            statement.setInt(1, eventID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                event = new Event();
                event.setEventID(resultSet.getInt("eventID"));
                event.setUserID(resultSet.getInt("userID"));
                event.setName(resultSet.getString("name"));
                event.setLocation(resultSet.getString("location"));
                event.setType(resultSet.getString("type"));
                event.setDescription(resultSet.getString("description"));
                event.setEntryFee(resultSet.getDouble("entryFee"));
                event.setCapacity(resultSet.getInt("capacity"));
                event.setStartDate(resultSet.getDate("startDate"));
                event.setEndDate(resultSet.getDate("endDate"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Event");
            while (resultSet.next()) {
                Event event = new Event();
                event.setEventID(resultSet.getInt("eventID"));
                event.setUserID(resultSet.getInt("userID"));
                event.setName(resultSet.getString("name"));
                event.setLocation(resultSet.getString("location"));
                event.setType(resultSet.getString("type"));
                event.setDescription(resultSet.getString("description"));
                event.setEntryFee(resultSet.getDouble("entryFee"));
                event.setCapacity(resultSet.getInt("capacity"));
                event.setStartDate(resultSet.getDate("startDate"));
                event.setEndDate(resultSet.getDate("endDate"));
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return events;
    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Event" + 
                    "SET userID = ?, name = ?, location = ?, type = ?, description = ?, entryFee = ?, capacity = ?, startDate = ?, endDate = ?" + 
                    "WHERE eventID = ?"
            );
            statement.setInt(1, event.getUserID());
            statement.setString(2, event.getName());
            statement.setString(3, event.getLocation());
            statement.setString(4, event.getType());
            statement.setString(5, event.getDescription());
            statement.setDouble(6, event.getEntryFee());
            statement.setInt(7, event.getCapacity());
            statement.setDate(8, event.getStartDate());
            statement.setDate(9, event.getEndDate());
            statement.setInt(10, event.getEventID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEvent(int eventID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Event WHERE eventID = ?");
            statement.setInt(1, eventID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

}
