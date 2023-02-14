package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.ITagDao;
import com.artmart.models.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao implements ITagDao{
    
    private Connection connection;

    public TagDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int addTag(Tag t) {
try {
      String sql = "INSERT INTO tags ( name) VALUES (?)";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, t.getName());
      st.executeUpdate();
      return 1;
    } catch (SQLException e) {
        System.err.println("Error occured");
         e.printStackTrace();
        return 0;
    }         }

    @Override
    public Tag getOneTag(int tag_id) {
Tag tagFound = null;
    try {
           PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tags WHERE tags_ID = ?"
            );
            statement.setInt(1, tag_id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                tagFound = new Tag(
                result.getInt("tag_id"),
                result.getString("name")
        );
            }
    } catch (SQLException e) {
      System.err.println("Error occured");
      e.printStackTrace();
    }
    return tagFound;       }

    @Override
    public List<Tag> getAllTags() {
List<Tag> tagsList = new ArrayList<>();
    try {
      String sql = "SELECT * FROM tags";
      PreparedStatement st = connection.prepareStatement(sql);
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        tagsList.add(new Tag(
          rs.getInt("tags_ID"),
          rs.getString("name")
        ));
      }
    } catch (SQLException e) {
     System.err.println("Error occured");
           e.printStackTrace();

    }
    return tagsList;         }

    @Override
    public boolean updateTag(int tag_id, Tag editedTag) {
try {
      String sql = "UPDATE tags SET name = ? WHERE tags_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setString(1, editedTag.getName());
      st.setInt(2, tag_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }          }

    @Override
    public boolean deleteTag(int tag_id) {
try {
      String sql = "DELETE FROM tags WHERE tags_ID = ?";
      PreparedStatement st = connection.prepareStatement(sql);
      st.setInt(1, tag_id);
      st.executeUpdate();
      return true;
    } catch (SQLException e) {
     System.err.println("Error occured");
     return false;
    }           }
    
}
