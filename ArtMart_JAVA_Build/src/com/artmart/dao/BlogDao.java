package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IBlogServiceDao;
import com.artmart.models.Blog;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogDao implements IBlogServiceDao {
    
    private Connection connection;

    public BlogDao() {
     try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
    

    @Override
    public int addBlog(Blog b) {
try {
      String sql = "INSERT INTO blogs (title, content, date, author) VALUES (?, ?, ?, ?)";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, b.getTitle());
      st.setString(2, b.getContent());
      st.setDate(3, (Date) b.getPublishDate());
      st.setInt(4, b.getAuthor());
      st.executeUpdate();
      return 1;
    } catch (SQLException e) {
        System.err.println("Error occured");
        return 0;
    }    }

    @Override
    public Blog getOneBlog(int blog_id) {
    Blog blogFound = null;
    try {
           PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM blogs WHERE blogs_ID = ?"
            );
            statement.setInt(1, blog_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                        blogFound = new Blog(
          result.getInt("blogs_ID"),
          result.getString("title"),
          result.getString("content"),
          result.getDate("date"),
          result.getInt("author")
        );
            }
    } catch (SQLException e) {
      System.err.println("Error occured");
      e.printStackTrace();
    }
    return blogFound;
    }
    

    @Override
     public List<Blog> getAllBlogs() {
    List<Blog> blogs = new ArrayList<>();
    try {
      String sql = "SELECT * FROM blogs";
      PreparedStatement st = connection.prepareStatement(sql);
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        blogs.add(new Blog(
          rs.getInt("blogs_ID"),
          rs.getString("title"),
          rs.getString("content"),
          rs.getDate("date"),
          rs.getInt("author")
        ));
      }
    } catch (SQLException e) {
     System.err.println("Error occured");
           e.printStackTrace();

    }
    return blogs;
    }

    @Override
    public boolean updateBlog(int blog_id,Blog editedBlog) {
     try {
      String sql = "UPDATE blogs SET title = ?, content = ? WHERE blogs_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, editedBlog.getTitle());
      st.setString(2, editedBlog.getContent());
      st.setInt(3, blog_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }
    }

    @Override
    public boolean deleteBlog(int blog_id) {
     try {
      String sql = "DELETE FROM blogs WHERE blogs_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setInt(1, blog_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }
    }
    
}
