/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Tag;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface ITagDao {
    public int addTag(Tag t); 
    public Tag getOneTag(int tag_id); 
    public List<Tag> getAllTags(); 
    public boolean  updateTag(int tag_id, Tag editedTag); 
    public boolean  deleteTag(int tag_id); 
}
