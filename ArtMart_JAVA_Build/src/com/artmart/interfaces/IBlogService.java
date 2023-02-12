package com.artmart.interfaces;
import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.Comment;
import java.util.List;

public interface IBlogService {
    //Blog DAO
    public int addBlog(Blog b); 
    public Blog getOneBlog(int blog_id); 
    public List<Blog> getAllBlogs(); 
    public boolean  updateBlog(int blog_id, Blog editedBlog); 
    public boolean  deleteBlog(int blog_id); 
    
    //Comment DAO
    public int addComment(Comment c); 
    public Comment getOneComment(int comment_id); 
    public List<Comment> getAllComments(); 
    public boolean  updateComment(int comment_id, Comment editedComment); 
    public boolean  deleteComment(int comment_id);
    
    //Blog Categories DAO
    public int addBlogCategories(BlogCategories bc); 
    public BlogCategories getOneBlogCategory(int blogsCategory_id); 
    public List<BlogCategories> getAllBlogCategories(); 
    public boolean  updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat); 
    public boolean  deleteBlogCategory(int blogsCategory_id); 
}
