package com.artmart.interfaces;

import com.artmart.models.Categories;
import java.sql.SQLException;
import java.util.List;

public interface ICategoriesDao {
   public int createCategories(Categories Categories) throws SQLException;
           public int updateCategories(int id, Categories categories) throws SQLException;
                   public int deleteCategories(int id) throws SQLException;
                           public Categories getCategoriesById(int CategoriesID) throws SQLException;
                                     public List<Categories> getAllCategories() throws SQLException;

}
