package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.models.Apply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplyDao {
       private CustomProductDao productDAO = new CustomProductDao();
    private final SQLConnection sqlConnection = SQLConnection.getInstance();

    // Create a new application
    public int createApply(Apply apply) throws SQLException {
        String query = "INSERT INTO Apply (customproduct_ID, artist_ID, status) VALUES (?, ?, ?)";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, apply.getCustomproduct_ID());
        statement.setInt(2, apply.getArtist_ID());
        statement.setString(3, apply.getStatus());
        return statement.executeUpdate();
    }

    // Update an existing application
    public boolean updateApply(int applyId, Apply apply) throws SQLException {
        String query = "UPDATE Apply SET customproduct_ID = ?, artist_ID = ?, status = ? WHERE apply_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, apply.getCustomproduct_ID());
        statement.setInt(2, apply.getArtist_ID());
        statement.setString(3, apply.getStatus());
        statement.setInt(4, applyId);
        statement.executeUpdate();
        return true;
    }

    // Delete an application
    public int deleteApply(int applyId) throws SQLException {
        String query = "DELETE FROM Apply WHERE apply_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, applyId);
      statement.executeUpdate();
     
             this.productDAO.deleteCustomProduct(this.getApplyId(applyId));
               return 1;
    }

    public int getApplyId(int id) throws SQLException {
        String query = "SELECT customproduct_ID FROM apply WHERE apply_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("customproduct_ID");
        } else {
            return 0;
        }}
    // Get an application by ID
    public Apply getApplyById(int applyId) throws SQLException {
        String query = "SELECT customproduct_ID, artist_ID, status FROM Apply WHERE apply_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, applyId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            int customProductId = result.getInt("customproduct_ID");
            int artistId = result.getInt("artist_ID");
            String status = result.getString("status");
            return new Apply(applyId, customProductId, artistId, status);
        } else {
            return null;
        }
    }

    // Get all applications
    public List<Apply> getAllApplies() throws SQLException {
        List<Apply> applies = new ArrayList<>();
        String query = "SELECT apply_ID, customproduct_ID, artist_ID, status FROM Apply ";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int applyId = result.getInt("apply_ID");
            int customProductId = result.getInt("customproduct_ID");
            int artistId = result.getInt("artist_ID");
            String status = result.getString("status");
            Apply x = new Apply(applyId, customProductId, artistId, status);
            applies.add(x);
        }
        return applies;
    }
      public List<Apply> getAllApplies1() throws SQLException {
        List<Apply> applies = new ArrayList<>();
        String query = "SELECT apply_ID, customproduct_ID, artist_ID, status FROM Apply WHERE status IN ('in progress')";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int applyId = result.getInt("apply_ID");
            int customProductId = result.getInt("customproduct_ID");
            int artistId = result.getInt("artist_ID");
            String status = result.getString("status");
            Apply x = new Apply(applyId, customProductId, artistId, status);
            applies.add(x);
        }
        return applies;
    }
         public List<Apply> getAllApplies2() throws SQLException {
        List<Apply> applies = new ArrayList<>();
        String query = "SELECT apply_ID, customproduct_ID, artist_ID, status FROM Apply WHERE status IN ('Pending')";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int applyId = result.getInt("apply_ID");
            int customProductId = result.getInt("customproduct_ID");
            int artistId = result.getInt("artist_ID");
            String status = result.getString("status");
            Apply x = new Apply(applyId, customProductId, artistId, status);
            applies.add(x);
        }
        return applies;
    }
         
          public List<Apply> getAllApplies3() throws SQLException {
        List<Apply> applies = new ArrayList<>();
        String query = "SELECT apply_ID, customproduct_ID, artist_ID, status FROM Apply WHERE status IN ('done')";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int applyId = result.getInt("apply_ID");
            int customProductId = result.getInt("customproduct_ID");
            int artistId = result.getInt("artist_ID");
            String status = result.getString("status");
            Apply x = new Apply(applyId, customProductId, artistId, status);
            applies.add(x);
        }
        return applies;
    }
 public String getCustomProductNameById(int customProductId) throws SQLException {
    String query = "SELECT p.name FROM product p INNER JOIN customProduct cp ON p.product_ID = cp.product_ID WHERE cp.custom_product_ID = ?";
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    statement.setInt(1, customProductId);
    ResultSet result = statement.executeQuery();
    if (result.next()) {
        return result.getString("name");
    } else {
        return null;
    }
}
 public String getartisttNameById(int customProductId) throws SQLException {
    String query = "SELECT p.name FROM user p INNER JOIN artist cp ON p.user_ID = cp.user_ID WHERE cp.artist_ID = ?";
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    statement.setInt(1, customProductId);
    ResultSet result = statement.executeQuery();
    if (result.next()) {
        return result.getString("name");
    } else {
        return null;
    }
}
 
 public boolean checkIfApplyExists(int customProductId) throws SQLException {
    String query = "SELECT COUNT(*) as count FROM Apply WHERE customproduct_ID = ?";
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    statement.setInt(1, customProductId);
    ResultSet result = statement.executeQuery();
    if (result.next()) {
        int count = result.getInt("count");
        return count > 0;
    } else {
        return false;
    }
}

}

