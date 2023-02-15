package com.artmart.interfaces;

import com.artmart.models.Categories;
import java.util.List;


public interface ICategoriesService {
       int createCategories(Categories Categories);
          int updateCategories(int id, Categories categories);
               int deleteCategories(int id);
                      Categories getCategoriesById(int CategoriesID);
                            List<Categories> getAllCategories();
}
