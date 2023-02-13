/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.interfaces.IClientDao;
import com.artmart.models.Client;
import com.artmart.services.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author 21697
 */
public class ClientDao implements IClientDao {

    private Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createAccountC(Client client) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user ( name, email, birthday, phoneNumber, username, password, dateOfCreation, role) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setDate(3, client.getBirthday());
            statement.setInt(4, client.getPhone_nbr());
            statement.setString(5, client.getUsername());
            statement.setString(6, client.getPwd());
            statement.setTimestamp(7, timestamp);
            statement.setString(8, client.getRole());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                return 0;
            }
            PreparedStatement clientStatement = connection.prepareStatement(
                    "INSERT INTO artist (user_ID, nbr_demands,nbr_orders) "
                    + "VALUES ( ?,?,? )"
            );
            clientStatement.setInt(1, userId);
            clientStatement.setInt(2, client.getNbr_cus_demands());
            clientStatement.setInt(3, client.getNbr_orders());

            clientStatement.executeUpdate();
            return 1;

        } catch (SQLException e) {
            System.err.println("Error occured");
        }

        return 0;
    }

    @Override
    public Client getClient(int user_id) {
 try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM artis WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client();
                client.setClient_id(resultSet.getInt("artist_ID"));
                client.setUser_id(resultSet.getInt("user_ID"));
                client.setNbr_cus_demands(resultSet.getInt("nbr_demands"));
                client.setNbr_orders(resultSet.getInt("nbr_orders"));

                return client;
            }
        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return null;    }

    @Override
    public boolean deleteAccountC(int user_id) {
try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM artist WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            statement.executeUpdate();
            UserService user_ser = new UserService();

            return user_ser.deleteAccountU(user_id);
        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return false;    }

    @Override
    public boolean updateAccountC(int user_id, Client client) {
try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE user nbr_demands = ?, nbr_orders = ? SET  WHERE user_ID = ?"
            );
            statement.setInt(1, client.getNbr_cus_demands());
            statement.setInt(2, client.getNbr_orders());
            
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return false;      }

}
