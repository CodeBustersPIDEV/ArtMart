/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.dao;

import com.artmart.interfaces.IEventDao;
import com.artmart.models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public class EventDao implements IEventDao{
    
    private Connection connection;

    public EventDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public int createEvent(Event event) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                // `event`(`event_ID`, `name`, `location`, `type`, `description`, `entryFee`, `capacity`, `start_date`, `end_date`
                "INSERT INTO event (name, location, type, description, entryFee, capacity, startDate, endDate, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());
            statement.setString(3, event.getType());
            statement.setString(4, event.getDescription());
            statement.setDouble(5, event.getEntryFee());
            statement.setInt(6,event.getCapacity());
            statement.setDate(7, event.getStartDate());
            statement.setDate(8, event.getEndDate());
            statement.setInt(9, event.getUserID());
             result = statement.executeUpdate();
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return result;    
    }

    @Override
    public Event getEvent(int eventID) {
        Event event = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Event WHERE event_ID = ?"
            );
            statement.setInt(1, eventID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                event = new Event();
                event.setEventID(resultSet.getInt("event_ID"));
                event.setName(resultSet.getString("name"));
                event.setLocation(resultSet.getString("location"));
                event.setType(resultSet.getString("type"));
                event.setDescription(resultSet.getString("description"));
                event.setEntryFee(resultSet.getDouble("entryFee"));
                event.setCapacity(resultSet.getInt("capacity"));
                event.setStartDate(resultSet.getDate("startDate"));
                event.setEndDate(resultSet.getDate("endDate"));
                event.setUserID(resultSet.getInt("user_id"));
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
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
                event.setEventID(resultSet.getInt("event_ID"));
                event.setName(resultSet.getString("name"));
                event.setLocation(resultSet.getString("location"));
                event.setType(resultSet.getString("type"));
                event.setDescription(resultSet.getString("description"));
                event.setEntryFee(resultSet.getDouble("entryFee"));
                event.setCapacity(resultSet.getInt("capacity"));
                event.setStartDate(resultSet.getDate("startDate"));
                event.setEndDate(resultSet.getDate("endDate"));
                event.setUserID(resultSet.getInt("user_id"));
                events.add(event);
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return events;
    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE Event SET name = ?, location = ?, type = ?, description = ?, entryFee = ?, capacity = ?, startDate = ?, endDate = ?, user_id = ? WHERE event_ID = ?");
            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());
            statement.setString(3, event.getType());
            statement.setString(4, event.getDescription());
            statement.setDouble(5, event.getEntryFee());
            statement.setInt(6,event.getCapacity());
            statement.setDate(7, event.getStartDate());
            statement.setDate(8, event.getEndDate());
            statement.setInt(9, event.getUserID());
            statement.setInt(10, event.getEventID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteEvent(int eventID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Event WHERE event_ID = ?");
            statement.setInt(1, eventID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }
    
}
