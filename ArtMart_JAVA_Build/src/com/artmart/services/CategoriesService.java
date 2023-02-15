
package com.artmart.services;

import com.artmart.dao.CategoriesDao;
import com.artmart.interfaces.ICategoriesDao;
import com.artmart.models.Categories;
import java.sql.SQLException;
import java.util.List;


public class CategoriesService implements ICategoriesDao {
    private CategoriesDao x = new CategoriesDao();

    @Override
    public int createCategories(Categories Categories) throws SQLException {
return x.createCategories(Categories);
        }

    @Override
    public int updateCategories(int id, Categories categories) throws SQLException {
      return x.updateCategories(id, categories);
    }

    @Override
    public int deleteCategories(int id) throws SQLException {
     return x.deleteCategories(id);
    }

    @Override
    public Categories getCategoriesById(int CategoriesID) throws SQLException {
       return x.getCategoriesById(CategoriesID);
    }

    @Override
    public List<Categories> getAllCategories() throws SQLException {
     return x.getAllCategories();
    }
}
