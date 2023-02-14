package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IArtistDao;
import com.artmart.models.Artist;
import com.artmart.services.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ArtistDao implements IArtistDao {

    private Connection connection;

    public ArtistDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createAccountAr(Artist artist) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user ( name, email, birthday, phoneNumber, username, password, dateOfCreation, role) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setString(1, artist.getName());
            statement.setString(2, artist.getEmail());
            statement.setDate(3, artist.getBirthday());
            statement.setInt(4, artist.getPhone_nbr());
            statement.setString(5, artist.getUsername());
            statement.setString(6, artist.getPwd());
            statement.setTimestamp(7, timestamp);
            statement.setString(8, artist.getRole());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                return 0;
            }
            PreparedStatement clientStatement = connection.prepareStatement(
                    "INSERT INTO artist (user_ID, nbr_artwork,bio) "
                    + "VALUES ( ?,?,? )"
            );
            clientStatement.setInt(1, userId);
            clientStatement.setInt(2, artist.getNbr_artwork());
            clientStatement.setString(3, artist.getBio());

            clientStatement.executeUpdate();
            return 1;

        } catch (SQLException e) {
            System.err.println("Error occured");
        }

        return 0;
    }

    @Override
    public Artist getArtist(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM artis WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Artist artist = new Artist();
                artist.setArtist_id(resultSet.getInt("artist_ID"));
                artist.setUser_id(resultSet.getInt("user_ID"));
                artist.setBio(resultSet.getString("bio"));
                artist.setNbr_artwork(resultSet.getInt("nbr_artwork"));

                return artist;
            }
        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return null;
    }

    @Override
    public boolean deleteAccountAr(int user_id) {
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
        return false;
    }

    @Override
    public boolean updateAccountAr(int user_id, Artist artist) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE user bio = ?, nbr_artwork = ? SET  WHERE user_ID = ?"
            );
            statement.setString(1, artist.getBio());
            statement.setInt(2, artist.getNbr_artwork());
            
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return false;   
    }

}
