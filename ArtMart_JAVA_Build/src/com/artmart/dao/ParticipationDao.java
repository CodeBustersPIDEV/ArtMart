/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IParticipationDao;
import com.artmart.models.Participation;
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
public class ParticipationDao implements IParticipationDao{

    private Connection connection;

    public ParticipationDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }    

    @Override
    public int createParticipation(Participation participation) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO participation (userID, eventID, attendanceStatus) "
                   +"VALUES (?, ?, ?)"
            );
            statement.setInt(1, participation.getUserID());
            statement.setInt(2, participation.getEventlD());
            statement.setString(3, participation.getAttendanceStatus());
            //statement.setDate(4, participation.getRegistrationDate());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return result;
    }

    @Override
    public Participation getParticipation(int participationID) {
        Participation participation = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM participation WHERE participationID = ?"
            );
            statement.setInt(1, participationID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                
                participation = new Participation();
                
                participation.setParticipationlD(resultSet.getInt("participationID"));
                participation.setUserID(resultSet.getInt("userID"));
                participation.setEventlD(resultSet.getInt("eventID"));
                participation.setAttendanceStatus(resultSet.getString("attendanceStatus"));
                participation.setRegistrationDate(resultSet.getDate("registrationDate"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return participation;
    }
    
    @Override
    public Participation getParticipationByID(int userID, int eventID) {
        Participation participation = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
//SELECT p.* FROM participation p INNER JOIN user u ON p.userID = u.user_ID INNER JOIN event e ON p.eventID = e.eventID WHERE p.userID = ? AND p.eventID = ?;
                "SELECT p.* FROM participation p INNER JOIN user u ON p.userID = u.user_ID INNER JOIN event e ON p.eventID = e.eventID WHERE p.userID = ? AND p.eventID = ?"
                    //"SELECT * FROM participation WHERE userID = ? AND eventID = ?"
            );
            statement.setInt(1, userID);
            statement.setInt(2, eventID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                
                participation = new Participation();
                
                participation.setParticipationlD(resultSet.getInt("participationID"));
                participation.setUserID(resultSet.getInt("userID"));
                participation.setEventlD(resultSet.getInt("eventID"));
                participation.setAttendanceStatus(resultSet.getString("attendanceStatus"));
                participation.setRegistrationDate(resultSet.getDate("registrationDate"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return participation;
    }

    @Override
    public List<Participation> getAllParticipations() {
        
        List<Participation> participations = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Participation");
            while (resultSet.next()) {
                
                Participation participation = new Participation();
                
                participation.setParticipationlD(resultSet.getInt("participationID"));
                participation.setUserID(resultSet.getInt("userID"));
                participation.setEventlD(resultSet.getInt("eventID"));
                participation.setAttendanceStatus(resultSet.getString("attendanceStatus"));
                participation.setRegistrationDate(resultSet.getDate("registrationDate"));
                participations.add(participation);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return participations;
    }

    @Override
    public boolean updateParticipation(int participationID, Participation participation) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE Participation SET attendanceStatus = ?, registrationDate = ?"
               +"WHERE participationID = ?"
            );
            //statement.setInt(1, event.getUserID());
            statement.setString(1, participation.getAttendanceStatus());
            statement.setDate(2, participation.getRegistrationDate());
            statement.setInt(3, participationID);
            
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteParticipation(int participationID) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Participation WHERE participationID = ?");
            statement.setInt(1, participationID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }
    
}
