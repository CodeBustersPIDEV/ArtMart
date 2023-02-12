package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.dao.BlogDao;
import com.artmart.interfaces.IBlogService;
import com.artmart.models.Blog;
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

    public BlogService() {
        try{
        this.connection = SQLConnection.getInstance().getConnection();
        this.blogDao = new BlogDao(this.connection);
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

}
