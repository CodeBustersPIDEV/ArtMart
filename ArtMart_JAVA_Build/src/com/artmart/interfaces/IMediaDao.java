package com.artmart.interfaces;

import com.artmart.models.Media;
import java.util.List;

public interface IMediaDao {

    public int addMedia(Media m);

    public Media getOneMedia(int media_id);
    
    public Media getOneMediaByBlogID(int blog_id);

    public List<Media> getAllMedias();

    public boolean updateMedia(int media_id, Media editedMedia);

    public boolean deleteMedia(int media_id);
}
