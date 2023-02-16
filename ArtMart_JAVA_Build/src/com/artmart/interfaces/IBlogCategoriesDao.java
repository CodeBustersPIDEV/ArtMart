package com.artmart.interfaces;

import com.artmart.models.BlogCategories;
import java.util.List;

public interface IBlogCategoriesDao {

    public int addBlogCategories(BlogCategories bc);

    public BlogCategories getOneBlogCategory(int blogsCategory_id);

    public List<BlogCategories> getAllBlogCategories();

    public boolean updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat);

    public boolean deleteBlogCategory(int blogsCategory_id);
}
