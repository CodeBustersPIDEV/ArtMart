package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IArtistDao;
import com.artmart.models.Artist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistDao implements IArtistDao {

    private Connection connection;
    private UserDao userDao;

    public ArtistDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createAccountAr(Artist artist) {
        try {
            PreparedStatement clientStatement = connection.prepareStatement(
                    "INSERT INTO artist (user_ID, nbr_artwork,bio) "
                    + "VALUES ( ?,?,? )"
            );
            clientStatement.setInt(1, userDao.createAccountU(artist));
            clientStatement.setInt(2, 0);
            clientStatement.setString(3, artist.getBio());

            clientStatement.executeUpdate();
            return 1;

        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return 0;
    }

    @Override
    public Artist getArtist(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM artist WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Artist artist = new Artist(userDao.getUser(user_id));
                artist.setArtist_id(resultSet.getInt("artist_ID"));
                artist.setUser_id(resultSet.getInt("user_ID"));
                artist.setBio(resultSet.getString("bio"));
                artist.setNbr_artwork(resultSet.getInt("nbr_artwork"));

                return artist;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
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
            boolean user = userDao.deleteAccountU(user_id);
            return user;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateAccountAr(int user_id, Artist artist) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE artist SET bio = ?, nbr_artwork = ?   WHERE user_ID = ?"
            );
            statement.setString(1, artist.getBio());
            statement.setInt(2, artist.getNbr_artwork());
            statement.setInt(3, user_id);

            statement.executeUpdate();
            boolean user = userDao.updateAccountU(user_id, artist);
            return user;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

}
