package com.artmart.interfaces;
import com.artmart.models.Blog;
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
}
