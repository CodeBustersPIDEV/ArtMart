/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.interfaces.IBlogCategoriesDao;
import com.artmart.models.BlogCategories;
import com.artmart.models.Comment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marwen
 */
public class BlogCategoriesDao implements IBlogCategoriesDao{
 
    private Connection connection;

    public BlogCategoriesDao(Connection connection) {
        this.connection = connection;
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
        System.err.println("Error occured");
         e.printStackTrace();
        return 0;
    }     }

    @Override
    public BlogCategories getOneBlogCategory(int blogsCategory_id) {
BlogCategories blogCategoryFound = null;
    try {
           PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM blogcategories WHERE blogsCategory_id = ?"
            );
            statement.setInt(1, blogsCategory_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                blogCategoryFound = new BlogCategories(
                result.getInt("blogsCategory_id"),
                result.getString("name")
        );
            }
    } catch (SQLException e) {
      System.err.println("Error occured");
      e.printStackTrace();
    }
    return blogCategoryFound;    }

    @Override
    public List<BlogCategories> getAllBlogCategories() {
 List<BlogCategories> blogCategoriesList = new ArrayList<>();
    try {
      String sql = "SELECT * FROM blogcategories";
      PreparedStatement st = connection.prepareStatement(sql);
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        blogCategoriesList.add(new BlogCategories(
          rs.getInt("blogsCategory_id"),
          rs.getString("name")
        ));
      }
    } catch (SQLException e) {
     System.err.println("Error occured");
           e.printStackTrace();

    }
    return blogCategoriesList;     }

    @Override
    public boolean updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat) {
try {
      String sql = "UPDATE blogcategories SET name = ? WHERE blogsCategory_id = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, editedBlogCat.getName());
      st.setInt(2, blogsCategory_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }      }

    @Override
    public boolean deleteBlogCategory(int blogsCategory_id) {
try {
      String sql = "DELETE FROM blogcategories WHERE blogsCategory_id = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setInt(1, blogsCategory_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }       }

}
