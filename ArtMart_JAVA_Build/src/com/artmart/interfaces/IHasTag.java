package com.artmart.interfaces;

import com.artmart.models.HasTag;
import java.util.List;

public interface IHasTag {

    public int addBlog2HasTag(HasTag ht);

    public List<HasTag> getAllTagsbyBlog(int blog_id);
    
    public HasTag getTagbyBlog(int blog_id);

    public boolean updateHasTag(int blog_id, HasTag editedHT);

    public boolean deleteHasTag(int blog_id);
}
