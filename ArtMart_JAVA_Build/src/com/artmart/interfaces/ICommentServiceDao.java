package com.artmart.interfaces;

import com.artmart.models.Comment;
import java.util.List;

public interface ICommentServiceDao {

    public int addComment(Comment c);

    public Comment getOneComment(int comment_id);

    public List<Comment> getAllComments();

    public boolean updateComment(int comment_id, Comment editedComment);

    public boolean deleteComment(int comment_id);
}
