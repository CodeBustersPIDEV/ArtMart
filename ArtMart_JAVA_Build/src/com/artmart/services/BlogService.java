package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.dao.BlogDao;
import com.artmart.dao.CommentDao;
import com.artmart.interfaces.IBlogService;
import com.artmart.models.Blog;
import com.artmart.models.Comment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BlogService implements IBlogService{
    
    private Connection connection;
    private BlogDao blogDao;
    private CommentDao commentDao;

    public BlogService() {
        try{
        this.connection = SQLConnection.getInstance().getConnection();
        this.blogDao = new BlogDao(this.connection);
        this.commentDao = new CommentDao(this.connection);
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
    
    
    
    @Override
    public int addBlog(Blog b){
        return this.blogDao.addBlog(b);

    
}

    @Override
    public Blog getOneBlog(int blog_id) {
        return this.blogDao.getOneBlog(blog_id);

    }

    @Override
    public List<Blog> getAllBlogs() {
    return this.blogDao.getAllBlogs();
    }

    @Override
    public boolean updateBlog(int blog_id,Blog editedBlog) {
         return this.blogDao.updateBlog(blog_id,editedBlog);

    }

    @Override
    public boolean deleteBlog(int blog_id) {
        return this.blogDao.deleteBlog(blog_id);
    }

    @Override
    public int addComment(Comment c) {
        return this.commentDao.addComment(c);
    }

    @Override
    public Comment getOneComment(int comment_id) {
return this.commentDao.getOneComment(comment_id);    }

    @Override
    public List<Comment> getAllComments() {
return this.commentDao.getAllComments();     }

    @Override
    public boolean updateComment(int comment_id, Comment editedComment) {
return this.commentDao.updateComment(comment_id,editedComment);     }

    @Override
    public boolean deleteComment(int comment_id) {
return this.commentDao.deleteComment(comment_id);     }

}
