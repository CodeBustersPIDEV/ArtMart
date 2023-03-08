package com.artmart.interfaces;

import com.artmart.models.Blog;
import java.util.List;

public interface IBlogServiceDao {

    public int addBlog(Blog b);

    public Blog getOneBlog(int blog_id);

    public Blog getOneBlogByTitle(String blog_title);

    public List<Blog> getAllBlogs();

    public List<Blog> getAllBlogsByUser(int user_id);

    public List<Blog> searchBlogsByTitle(String blog_title);

    public boolean updateBlog(int blog_id, Blog editedBlog);

    public boolean updateBlogRating(int blog_id, double blogRating);

    public boolean updateBlogViews(int blog_id);

    public boolean deleteBlog(int blog_id);
}
