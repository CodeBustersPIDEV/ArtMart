package com.artmart.dao;

import com.artmart.interfaces.ICommentServiceDao;
import com.artmart.models.Comment;
import java.sql.Connection;
import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDao implements ICommentServiceDao{
     private Connection connection;

    public CommentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int addComment(Comment c) {
try {
      String sql = "INSERT INTO comments ( content, date, author, blog_ID) VALUES (?, ?, ?, ?)";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, c.getContent());
      st.setDate(2, (Date) c.getPublishDate());
      st.setInt(3, c.getAuthor());      
      st.setInt(4, c.getBlog_id());
      st.executeUpdate();
      return 1;
    } catch (SQLException e) {
        System.err.println("Error occured");
         e.printStackTrace();
        return 0;
    } 
    }

    @Override
    public Comment getOneComment(int comment_id) {
    Comment commentFound = null;
    try {
           PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM comments WHERE comments_ID = ?"
            );
            statement.setInt(1, comment_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                commentFound = new Comment(
                result.getInt("comments_ID"),
                result.getString("content"),
                result.getDate("date"),
                result.getInt("author"),
                result.getInt("blog_ID")
        );
            }
    } catch (SQLException e) {
      System.err.println("Error occured");
      e.printStackTrace();
    }
    return commentFound;    }

    @Override
    public List<Comment> getAllComments() {
 List<Comment> comments = new ArrayList<>();
    try {
      String sql = "SELECT * FROM comments";
      PreparedStatement st = connection.prepareStatement(sql);
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        comments.add(new Comment(
          rs.getInt("comments_ID"),
          rs.getString("content"),
          rs.getDate("date"),
          rs.getInt("author"),
          rs.getInt("blog_ID")
        ));
      }
    } catch (SQLException e) {
     System.err.println("Error occured");
           e.printStackTrace();

    }
    return comments;    }

    @Override
    public boolean updateComment(int comment_id, Comment editedComment) {
try {
      String sql = "UPDATE comments SET content = ? WHERE comments_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, editedComment.getContent());
      st.setInt(2, comment_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }    }

    @Override
    public boolean deleteComment(int comment_id) {
  try {
      String sql = "DELETE FROM comments WHERE comments_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setInt(1, comment_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }    }
}
