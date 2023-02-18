package com.artmart.interfaces;

import com.artmart.models.Blog;
import java.util.List;

public interface IBlogServiceDao {

    public int addBlog(Blog b);

    public Blog getOneBlog(int blog_id);
    
    public Blog getOneBlogByTitle(String blog_title);

    public List<Blog> getAllBlogs();

    public boolean updateBlog(int blog_id, Blog editedBlog);

    public boolean deleteBlog(int blog_id);
}
