package com.artmart.services;

import com.artmart.dao.BlogDao;
import com.artmart.dao.CommentDao;
import com.artmart.dao.BlogCategoriesDao;
import com.artmart.dao.HasCategoryDao;
import com.artmart.dao.HasTagDao;
import com.artmart.dao.MediaDao;
import com.artmart.dao.TagDao;
import com.artmart.interfaces.IBlogService;
import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.Comment;
import com.artmart.models.HasCategory;
import com.artmart.models.HasTag;
import com.artmart.models.Media;
import com.artmart.models.Tag;
import java.util.List;

public class BlogService implements IBlogService {

    private final BlogDao blogDao = new BlogDao();
    private final CommentDao commentDao = new CommentDao();
    private final BlogCategoriesDao blogCategories = new BlogCategoriesDao();
    private final TagDao tag = new TagDao();
    private final MediaDao media = new MediaDao();
    private final HasCategoryDao hasCategory = new HasCategoryDao();
    private final HasTagDao hasTag = new HasTagDao();

    @Override
    public int addBlog(Blog b) {
        return this.blogDao.addBlog(b);
    }

    @Override
    public Blog getOneBlog(int blog_id) {
        return this.blogDao.getOneBlog(blog_id);

    }
    
    @Override
    public Blog getOneBlogByTitle(String blog_title) {
        return this.blogDao.getOneBlogByTitle(blog_title);

    }

    @Override
    public List<Blog> getAllBlogs() {
        return this.blogDao.getAllBlogs();
    }

    @Override
    public boolean updateBlog(int blog_id, Blog editedBlog) {
        return this.blogDao.updateBlog(blog_id, editedBlog);

    }

    @Override
    public boolean deleteBlog(int blog_id) {
        return this.blogDao.deleteBlog(blog_id);
    }

    @Override
    public int addComment(Comment c) {
        return this.commentDao.addComment(c);
    }

    @Override
    public Comment getOneComment(int comment_id) {
        return this.commentDao.getOneComment(comment_id);
    }

    @Override
    public List<Comment> getAllComments() {
        return this.commentDao.getAllComments();
    }

    @Override
    public boolean updateComment(int comment_id, Comment editedComment) {
        return this.commentDao.updateComment(comment_id, editedComment);
    }

    @Override
    public boolean deleteComment(int comment_id) {
        return this.commentDao.deleteComment(comment_id);
    }

    @Override
    public int addBlogCategories(BlogCategories bc) {
        return this.blogCategories.addBlogCategories(bc);
    }

    @Override
    public BlogCategories getOneBlogCategory(int blogsCategory_id) {
        return this.blogCategories.getOneBlogCategory(blogsCategory_id);
    }

    @Override
    public BlogCategories getOneBlogCategory(String blogsCategory_name) {
        return this.blogCategories.getOneBlogCategory(blogsCategory_name);
    }
    
    @Override
    public List<BlogCategories> getAllBlogCategories() {
        return this.blogCategories.getAllBlogCategories();
    }

    @Override
    public boolean updateBlogCategory(int blogsCategory_id, BlogCategories editedBlogCat) {
        return this.blogCategories.updateBlogCategory(blogsCategory_id, editedBlogCat);
    }

    @Override
    public boolean deleteBlogCategory(int blogsCategory_id) {
        return this.blogCategories.deleteBlogCategory(blogsCategory_id);
    }

    @Override
    public int addTag(Tag t) {
        return this.tag.addTag(t);
    }

    @Override
    public Tag getOneTag(int tag_id) {
        return this.tag.getOneTag(tag_id);
    }

    @Override
    public List<Tag> getAllTags() {
        return this.tag.getAllTags();
    }

    @Override
    public boolean updateTag(int tag_id, Tag editedTag) {
        return this.tag.updateTag(tag_id, editedTag);
    }

    @Override
    public boolean deleteTag(int tag_id) {
        return this.tag.deleteTag(tag_id);
    }

    @Override
    public int addMedia(Media m) {
        return this.media.addMedia(m);
    }

    @Override
    public Media getOneMedia(int media_id) {
        return this.media.getOneMedia(media_id);
    }

    @Override
    public List<Media> getAllMedias() {
        return this.media.getAllMedias();
    }

    @Override
    public boolean updateMedia(int media_id, Media editedMedia) {
        return this.media.updateMedia(media_id, editedMedia);
    }

    @Override
    public boolean deleteMedia(int media_id) {
        return this.media.deleteMedia(media_id);
    }

    @Override
    public int addBlog2HasCat(HasCategory hc) {
        return this.hasCategory.addBlog2HasCat(hc);
    }

    @Override
    public List<HasCategory> getAllCatbyBlog(int blog_id) {
        return this.hasCategory.getAllCatbyBlog(blog_id);
    }

    @Override
    public boolean updateHasCat(int blog_id, HasCategory editedHC) {
        return this.hasCategory.updateHasCat(blog_id, editedHC);
    }

    @Override
    public boolean deleteHasCat(int blog_id) {
        return this.hasCategory.deleteHasCat(blog_id);
    }

    @Override
    public int addBlog2HasTag(HasTag ht) {
        return this.hasTag.addBlog2HasTag(ht);
    }

    @Override
    public List<HasTag> getAllTagsbyBlog(int blog_id) {
        return this.hasTag.getAllTagsbyBlog(blog_id);
    }

    @Override
    public boolean updateHasTag(int blog_id, HasTag editedHT) {
        return this.hasTag.updateHasTag(blog_id,editedHT);
    }

    @Override
    public boolean deleteHasTag(int blog_id) {
        return this.hasTag.deleteHasTag(blog_id);
    }

}
