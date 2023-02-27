package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IHasCategory;
import com.artmart.models.HasCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HasCategoryDao implements IHasCategory {

    private Connection connection;

    public HasCategoryDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int addBlog2HasCat(HasCategory hc) {
        try {
            String sql = "INSERT INTO has_blog_category (blog_id, category_id) VALUES (?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, hc.getBlog_id());
            st.setInt(2, hc.getCategory_id());
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<HasCategory> getAllCatbyBlog(int blog_id) {
        List<HasCategory> blog_hasCat = new ArrayList<>();
        try {
            String sql = "SELECT * FROM has_blog_category WHERE blog_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blog_id);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                blog_hasCat.add(new HasCategory(
                        rs.getInt("id"),
                        rs.getInt("blog_id"),
                        rs.getInt("category_id")
                ));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return blog_hasCat;
    }

    @Override
    public HasCategory getCatbyBlog(int blog_id) {
        HasCategory catFound = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM has_blog_category WHERE blog_id = ?"
            );
            statement.setInt(1, blog_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                catFound = new HasCategory(
                        result.getInt("id"),
                        result.getInt("blog_id"),
                        result.getInt("category_id")
                );
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return catFound;
    }

    @Override
    public boolean updateHasCat(int blog_id, HasCategory editedHC) {
        try {
            String sql = "UPDATE has_blog_category SET category_id = ? WHERE blog_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, editedHC.getCategory_id());
            st.setInt(2, blog_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteHasCat(int blog_id) {
        try {
            String sql = "DELETE FROM has_blog_category WHERE blog_id = ?";
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
