package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IActivityDao;
import com.artmart.models.Activity;
import com.artmart.models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao implements IActivityDao {

    private Connection connection;

    public ActivityDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createActivity(Activity activity) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO activity (eventID, date, title, host) "
                    +"VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, activity.getEventID());
            statement.setDate(2, activity.getDate());
            statement.setString(3, activity.getTitle());
            statement.setString(4, activity.getHost());
            result = statement.executeUpdate();
            System.out.println(result);
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
                    "SELECT * FROM activity WHERE activityID = ?"
            );
            statement.setInt(1, activityID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                activity = new Activity();
                activity.setActivityID(resultSet.getInt("activityID"));
                activity.setEventID(resultSet.getInt("eventID"));
                activity.setDate(resultSet.getDate("date"));
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
                activity.setActivityID(resultSet.getInt("activityID"));
                activity.setEventID(resultSet.getInt("eventID"));
                activity.setDate(resultSet.getDate("date"));
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
    public boolean updateActivity(int activityID, Activity activity) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Activity SET eventID = ?, startDate = ?, title = ?, host = ? WHERE activityID = ?");
            statement.setInt(1, activity.getEventID());
            statement.setDate(2, activity.getDate());
            statement.setString(3, activity.getTitle());
            statement.setString(4, activity.getHost());
            statement.setInt(5, activityID);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteActivity(int activityID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Activity WHERE activityID = ?");
            statement.setInt(1, activityID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public List<Activity> searchActivityByTitle(String title, int userID) {
        List<Activity> activities = new ArrayList<>();
        try{
            String query = "SELECT * FROM activity a JOIN event e ON a.eventID = e.eventID WHERE title LIKE ? AND e.userID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            statement.setInt(2, userID);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Activity activity = this.getActivity(resultSet.getInt("activityID"));
                activities.add(activity);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return activities;
    }

    @Override
    public List<Activity> getAllActivitiesByID(int userID) {
        List<Activity> activities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM activity a JOIN event e ON a.eventID = e.eventID WHERE e.userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setActivityID(resultSet.getInt("activityID"));
                activity.setEventID(resultSet.getInt("eventID"));
                activity.setDate(resultSet.getDate("date"));
                activity.setTitle(resultSet.getString("title"));
                activity.setHost(resultSet.getString("host"));
                activities.add(activity);
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return activities;
    }
}
