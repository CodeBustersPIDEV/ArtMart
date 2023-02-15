/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.HasCategory;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface IHasCategory {
    public int addBlog2HasCat(HasCategory hc);
    public List<HasCategory> getAllCatbyBlog(int blog_id);
    public boolean updateHasCat(int blog_id , HasCategory editedHC);
    public boolean deleteHasCat(int blog_id);

}
