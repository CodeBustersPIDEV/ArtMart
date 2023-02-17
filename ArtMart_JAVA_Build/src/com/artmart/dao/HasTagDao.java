package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IHasTag;
import com.artmart.models.HasTag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HasTagDao implements IHasTag {

    private Connection connection;

    public HasTagDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int addBlog2HasTag(HasTag ht) {
        try {
            String sql = "INSERT INTO blog_tags (blog_id, tag_id) VALUES (?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ht.getBlog_id());
            st.setInt(2, ht.getTag_id());
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<HasTag> getAllTagsbyBlog(int blog_id) {
        List<HasTag> blog_hasTag = new ArrayList<>();
        try {
            String sql = "SELECT * FROM blog_tags WHERE blog_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blog_id);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                blog_hasTag.add(new HasTag(
                        rs.getInt("id"),
                        rs.getInt("blog_id"),
                        rs.getInt("tag_id")
                ));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return blog_hasTag;
    }

    @Override
    public boolean updateHasTag(int blog_id, HasTag editedHT) {
        try {
            String sql = "UPDATE blog_tags SET tag_id = ? WHERE blog_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, editedHT.getTag_id());
            st.setInt(2, blog_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteHasTag(int blog_id) {
        try {
            String sql = "DELETE FROM blog_tags WHERE blog_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blog_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

}
