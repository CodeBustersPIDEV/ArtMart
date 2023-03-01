package com.artmart.interfaces;

import com.artmart.models.HasCategory;
import java.util.List;

public interface IHasCategory {

    public int addBlog2HasCat(HasCategory hc);

    public List<HasCategory> getAllCatbyBlog(int blog_id);
    
    public HasCategory getCatbyBlog(int blog_id);

    public boolean updateHasCat(int blog_id, HasCategory editedHC);

    public boolean deleteHasCat(int blog_id);

}
