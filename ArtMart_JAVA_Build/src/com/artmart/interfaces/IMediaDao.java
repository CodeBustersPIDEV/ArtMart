/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Media;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface IMediaDao {
     public int addMedia(Media m); 
    public Media getOneMedia(int media_id); 
    public List<Media> getAllMedias(); 
    public boolean  updateMedia(int media_id, Media editedMedia); 
    public boolean  deleteMedia(int media_id);
}
