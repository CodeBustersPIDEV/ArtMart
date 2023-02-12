/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Blog;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface IBlogServiceDao {
    public int addBlog(Blog b); 
    public Blog getOneBlog(int blog_id); 
    public List<Blog> getAllBlogs(); 
    public boolean  updateBlog(int blog_id, Blog editedBlog); 
    public boolean  deleteBlog(int blog_id); 
}
