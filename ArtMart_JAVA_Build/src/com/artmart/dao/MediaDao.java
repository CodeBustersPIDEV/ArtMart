package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IMediaDao;
import com.artmart.models.Media;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaDao implements IMediaDao {

    private Connection connection;

    public MediaDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int addMedia(Media m) {
        try {
            String sql = "INSERT INTO media (media_ID,file_name,file_type,file_path,blog_id) VALUES (?,?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, m.getId());
            st.setString(2, m.getFile_name());
            st.setString(3, m.getFile_type());
            st.setString(4, m.getFile_path());
            st.setInt(5, m.getBlog_id());
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }
    }

    @Override
    public Media getOneMedia(int media_id) {
        Media mediaFound = null;
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM media WHERE media_ID = ?"
            );
            st.setInt(1, media_id);

            ResultSet result = st.executeQuery();
            if (result.next()) {
                mediaFound = new Media(
                        result.getInt("media_ID"),
                        result.getString("file_name"),
                        result.getString("file_type"),
                        result.getString("file_path"),
                        result.getInt("blog_id")
                );
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return mediaFound;
    }

    @Override
    public List<Media> getAllMedias() {
        List<Media> mediaList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM media";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                mediaList.add(new Media(
                        rs.getInt("media_ID"),
                        rs.getString("file_name"),
                        rs.getString("file_type"),
                        rs.getString("file_path"),
                        rs.getInt("blog_id")
                ));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return mediaList;
    }

    @Override
    public boolean updateMedia(int media_id, Media editedMedia) {
        try {
            String sql = "UPDATE media SET file_name = ?,file_type = ?,file_path = ? WHERE media_ID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, editedMedia.getFile_name());
            st.setString(2, editedMedia.getFile_type());
            st.setString(3, editedMedia.getFile_path());
            st.setInt(4, media_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMedia(int media_id) {
        try {
            String sql = "DELETE FROM media WHERE media_ID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, media_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

}
