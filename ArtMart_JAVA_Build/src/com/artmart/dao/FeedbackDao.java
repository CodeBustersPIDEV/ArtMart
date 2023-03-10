package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IFeedbackDao;
import com.artmart.models.EventReport;
import com.artmart.models.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao implements IFeedbackDao {

    private Connection connection;

    public FeedbackDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createFeedback(Feedback feedback) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO feedback (eventID, userID, rating, comment) "
                    + "VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, feedback.getEventID());
            statement.setInt(2, feedback.getUserID());
            statement.setInt(3, feedback.getRating());
            statement.setString(4, feedback.getComment());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return result;
    }

    @Override
    public Feedback getFeedback(int feedbackID) {
        Feedback feedback = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Feedback"
                    + "WHERE feedbackID = ?"
            );
            statement.setInt(1, feedbackID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                feedback = new Feedback();
                feedback.setFeedbackID(resultSet.getInt("feedbackID"));
                feedback.setEventID(resultSet.getInt("eventID"));
                feedback.setEventID(resultSet.getInt("userID"));
                feedback.setRating(resultSet.getInt("rating"));
                feedback.setComment(resultSet.getString("comment"));
                feedback.setDate(resultSet.getDate("date"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return feedback;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Feedback");
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackID(resultSet.getInt("feedbackID"));
                feedback.setEventID(resultSet.getInt("eventID"));
                feedback.setEventID(resultSet.getInt("userID"));
                feedback.setRating(resultSet.getInt("rating"));
                feedback.setComment(resultSet.getString("comment"));
                feedback.setDate(resultSet.getDate("date"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return feedbacks;
    }

    @Override
    public boolean updateFeedback(int feedbackID, Feedback feedback) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Feedback"
                    + "SET eventID = ?, userID = ?, rating = ?, comment = ?, date = ?"
                    + "WHERE feedbackID = ?"
            );
            statement.setInt(1, feedback.getEventID());
            statement.setInt(2, feedback.getUserID());
            statement.setInt(3, feedback.getRating());
            statement.setString(4, feedback.getComment());
            statement.setDate(5, feedback.getDate());
            statement.setInt(6, feedbackID);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteFeedback(int feedbackID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Feedback"
                    + "WHERE feedbackID = ?"
            );
            statement.setInt(1, feedbackID);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }
    
    
    @Override
    public List<Feedback> getAllFeedbacksByID(int eventID) {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Feedback WHERE eventID = ?");
            statement.setInt(1, eventID);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackID(resultSet.getInt("feedbackID"));
                feedback.setEventID(resultSet.getInt("eventID"));
                feedback.setUserID(resultSet.getInt("userID"));
                feedback.setRating(resultSet.getInt("rating"));
                feedback.setComment(resultSet.getString("comment"));
                feedback.setDate(resultSet.getDate("date"));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println(e.getCause().getMessage());
        }
        return feedbacks;
    }

}
