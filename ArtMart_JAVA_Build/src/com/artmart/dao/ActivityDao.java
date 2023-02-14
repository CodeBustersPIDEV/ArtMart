/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.dao;

import com.artmart.interfaces.IActivityDao;
import com.artmart.models.Activity;
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
public class ActivityDao implements IActivityDao {

    private Connection connection;

    public ActivityDao(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public int createActivity(Activity activity) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO activity (event_ID, startDate, endDate, title, host) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, activity.getEventID());
            statement.setDate(2, activity.getStartDate());
            statement.setDate(3, activity.getEndDate());
            statement.setString(4, activity.getTitle());
            statement.setString(5, activity.getHost());
             result = statement.executeUpdate();
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return result;    
    }

    @Override
    public Activity getActivity(int activityID) {
        Activity activity = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM activity WHERE activity_ID = ?"
            );
            statement.setInt(1, activityID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                activity = new Activity();
                activity.setEventID(resultSet.getInt("activity_ID"));
                activity.setEventID(resultSet.getInt("event_ID"));
                activity.setStartDate(resultSet.getDate("startDate"));
                activity.setEndDate(resultSet.getDate("endDate"));
                activity.setTitle(resultSet.getString("title"));
                activity.setHost(resultSet.getString("host"));
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return activity;
    }

    @Override
    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM activity");
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setEventID(resultSet.getInt("activity_ID"));
                activity.setEventID(resultSet.getInt("event_ID"));
                activity.setStartDate(resultSet.getDate("startDate"));
                activity.setEndDate(resultSet.getDate("endDate"));
                activity.setTitle(resultSet.getString("title"));
                activity.setHost(resultSet.getString("host"));
                activities.add(activity);
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return activities;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        try {//event_ID, startDate, endDate, title, host
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE Activity SET event_ID = ?, startDate = ?, endDate = ?, title = ?, host = ? WHERE activity_ID = ?");
            statement.setInt(1, activity.getEventID());
            statement.setDate(2, activity.getStartDate());
            statement.setDate(3, activity.getEndDate());            
            statement.setString(4, activity.getTitle());
            statement.setString(5, activity.getHost());
            statement.setInt(6,activity.getActivityID());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }
    
    @Override
    public boolean deleteActivity(int activityID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Activity WHERE activity_ID = ?");
            statement.setInt(1, activityID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }
    
    
}
