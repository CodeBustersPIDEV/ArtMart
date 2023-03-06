package com.artmart.interfaces;

import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.Comment;
import com.artmart.models.HasCategory;
import com.artmart.models.HasTag;
import com.artmart.models.Media;
import com.artmart.models.Tag;
import java.util.List;

public interface IBlogService {

    public int addBlog(Blog b);

    public Blog getOneBlog(int blog_id);

    public Blog getOneBlogByTitle(String blog_title);

    public List<Blog> getAllBlogs();

    public List<Blog> getAllBlogsByUser(int user_id);

    public List<Blog> searchBlogsByTitle(String blog_title);

    public boolean updateBlog(int blog_id, Blog editedBlog);
    
    public boolean updateBlogRating(int blog_id, double blogRating);

    public boolean deleteBlog(int blog_id);

    public int addComment(Comment c);

    public Comment getOneComment(int comment_id);

    public List<Comment> getAllComments(int blog_id);

    public boolean updateComment(int comment_id, Comment editedComment);
    
    public double calculateRating(int blog_id);

    public boolean deleteComment(int comment_id);

    public boolean deleteAllComments(int blog_id);

    public int addBlogCategories(BlogCategories bc);

    public BlogCategories getOneBlogCategory(int blogsCategory_id);

    public BlogCategories getOneBlogCategory(String blogsCategory_name);

    public List<BlogCategories> getAllBlogCategories();

    public boolean updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat);

    public boolean deleteBlogCategory(int blogsCategory_id);

    public int addTag(Tag t);

    public Tag getOneTag(int tag_id);

    public Tag getOneTagByName(String tag_name);

    public List<Tag> getAllTags();

    public boolean updateTag(int tag_id, Tag editedTag);

    public boolean deleteTag(int tag_id);

    public int addMedia(Media m);

    public Media getOneMedia(int media_id);

    public Media getOneMediaByBlogID(int blog_id);

    public List<Media> getAllMedias();

    public boolean updateMedia(int media_id, Media editedMedia);

    public boolean deleteMedia(int media_id);

    public boolean deleteMediaByBlogID(int blog_id);

    public int addBlog2HasCat(HasCategory hc);

    public List<HasCategory> getAllCatbyBlog(int blog_id);

    public HasCategory getCatbyBlog(int blog_id);

    public boolean updateHasCat(int blog_id, HasCategory editedHC);

    public boolean deleteHasCat(int blog_id);

    public int addBlog2HasTag(HasTag ht);

    public List<HasTag> getAllTagsbyBlog(int blog_id);

    public HasTag getTagbyBlog(int blog_id);

    public boolean updateHasTag(int blog_id, HasTag editedHT);

    public boolean deleteHasTag(int blog_id);
}
