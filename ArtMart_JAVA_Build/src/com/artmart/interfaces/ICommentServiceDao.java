/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Comment;
import java.util.List;

/**
 *
 * @author marwen
 */
public interface ICommentServiceDao {
    public int addComment(Comment c); 
    public Comment getOneComment(int comment_id); 
    public List<Comment> getAllComments(); 
    public boolean  updateComment(int comment_id, Comment editedComment); 
    public boolean  deleteComment(int comment_id); 
}
