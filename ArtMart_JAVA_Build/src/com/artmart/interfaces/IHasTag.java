/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.HasTag;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface IHasTag {
     public int addBlog2HasTag(HasTag ht);
    public List<HasTag> getAllTagsbyBlog(int blog_id);
    public boolean updateHasTag(int blog_id , HasTag editedHT);
    public boolean deleteHasTag(int blog_id);
}
