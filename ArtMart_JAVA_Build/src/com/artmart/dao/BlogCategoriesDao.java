package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IBlogCategoriesDao;
import com.artmart.models.BlogCategories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogCategoriesDao implements IBlogCategoriesDao {

    private Connection connection;

    public BlogCategoriesDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int addBlogCategories(BlogCategories bc) {
        try {
            String sql = "INSERT INTO blogcategories ( name) VALUES (?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, bc.getName());
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }
    }

    @Override
    public BlogCategories getOneBlogCategory(int blogsCategory_id) {
        BlogCategories blogCategoryFound = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM blogcategories WHERE categories_ID = ?"
            );
            statement.setInt(1, blogsCategory_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                blogCategoryFound = new BlogCategories(
                        result.getInt("categories_ID"),
                        result.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return blogCategoryFound;
    }

        @Override
    public BlogCategories getOneBlogCategory(String blogsCategory_name) {
        BlogCategories blogCategoryFound = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM blogcategories WHERE name = ?"
            );
            statement.setString(1, blogsCategory_name);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                blogCategoryFound = new BlogCategories(
                        result.getInt("categories_ID"),
                        result.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return blogCategoryFound;
    }
    
    @Override
    public List<BlogCategories> getAllBlogCategories() {
        List<BlogCategories> blogCategoriesList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM blogcategories";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                blogCategoriesList.add(new BlogCategories(
                        rs.getInt("categories_ID"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return blogCategoriesList;
    }

    @Override
    public boolean updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat) {
        try {
            String sql = "UPDATE blogcategories SET name = ? WHERE categories_ID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, editedBlogCat.getName());
            st.setInt(2, blogsCategory_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBlogCategory(int blogsCategory_id) {
        try {
            String sql = "DELETE FROM blogcategories WHERE categories_ID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogsCategory_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }

}
