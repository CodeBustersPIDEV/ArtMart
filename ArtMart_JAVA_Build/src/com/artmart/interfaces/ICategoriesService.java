package com.artmart.interfaces;

import com.artmart.models.Categories;
import java.sql.SQLException;
import java.util.List;

public interface ICategoriesService {

    int createCategories(Categories Categories);

    boolean updateCategories(int id, Categories categories);

    int deleteCategories(int id);

    Categories getCategoriesById(int CategoriesID);

    List<Categories> getAllCategories();
    public String getCategoryNameById(int categoryId);
}
