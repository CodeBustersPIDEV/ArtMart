/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.BlogCategories;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface IBlogCategoriesDao {
    public int addBlogCategories(BlogCategories bc); 
    public BlogCategories getOneBlogCategory(int blogsCategory_id); 
    public List<BlogCategories> getAllBlogCategories(); 
    public boolean  updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat); 
    public boolean  deleteBlogCategory(int blogsCategory_id); 
}
