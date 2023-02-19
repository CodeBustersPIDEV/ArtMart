package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IClientDao;
import com.artmart.models.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDao implements IClientDao {

    private Connection connection;
    private UserDao userDao = new UserDao();

    public ClientDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createAccountC(Client client) {
        try {
            PreparedStatement clientStatement = connection.prepareStatement(
                    "INSERT INTO client (user_ID, nbr_demands,nbr_orders) "
                    + "VALUES ( ?,?,? )"
            );
            client.setRole("client");
            clientStatement.setInt(1,userDao.createAccountU(client));
            clientStatement.setInt(2,client.getNbr_cus_demands());
            clientStatement.setInt(3,client.getNbr_orders());

            clientStatement.executeUpdate();
            return 1;

        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return 0;
    }

    @Override
    public Client getClient(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM client WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(userDao.getUser(user_id));
                client.setClient_id(resultSet.getInt("artist_ID"));
                client.setUser_id(resultSet.getInt("user_ID"));
                client.setNbr_cus_demands(resultSet.getInt("nbr_demands"));
                client.setNbr_orders(resultSet.getInt("nbr_orders"));

                return client;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteAccountC(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM client WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            statement.executeUpdate();
            boolean user = userDao.deleteAccountU(user_id);
            return user;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateAccountC(int user_id, Client client) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE client SET nbr_demands = ?, nbr_orders = ?   WHERE user_ID = ?"
            );
            statement.setInt(1, client.getNbr_cus_demands());
            statement.setInt(2, client.getNbr_orders());
            statement.setInt(3, user_id);

            statement.executeUpdate();
            boolean user = userDao.updateAccountU(user_id, client);
            return user;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

}
