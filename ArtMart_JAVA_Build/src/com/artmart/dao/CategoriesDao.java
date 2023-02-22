package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.ICategoriesDao;
import com.artmart.models.Categories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDao implements ICategoriesDao {

    private final SQLConnection sqlConnection = SQLConnection.getInstance();

    @Override
    public int createCategories(Categories categories) throws SQLException {
        String query = "INSERT INTO Categories (name) VALUES (?)";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setString(1, categories.getName());

        return statement.executeUpdate();
    }

    @Override
public boolean updateCategories(int id, Categories categories) throws SQLException {
    String query = "UPDATE Categories SET name = ? WHERE Categories_ID = ?";
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    statement.setString(1, categories.getName());
    statement.setInt(2, id);

    statement.executeUpdate();
    return true;
}


    @Override
    public int deleteCategories(int id) throws SQLException {
        String query = "DELETE FROM Categories WHERE Categories_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeUpdate();

    }

    @Override
    public Categories getCategoriesById(int CategoriesID) throws SQLException {
        String query = "SELECT categories_ID, name FROM Categories WHERE Categories_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, CategoriesID);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            String name = result.getString("name");
            return new Categories(CategoriesID, name);
        } else {
            return null;
        }
    }

    @Override
    public List<Categories> getAllCategories() throws SQLException {
        List<Categories> categoriess = new ArrayList<>();
        String query = "SELECT categories_ID, name FROM Categories";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int CategoriesID = result.getInt("categories_ID");
            String name = result.getString("name");
            Categories x = new Categories(CategoriesID, name);
            categoriess.add(x);
        }
        return categoriess;
    }

}
