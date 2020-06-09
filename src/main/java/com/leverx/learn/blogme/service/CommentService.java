package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Comment;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
public interface CommentService {

   List<Comment> getCommentsByPostId(Integer articleId);

   Comment addComment(Integer postId, Comment comment);

   Comment getComment(Integer commentId);

   void removeComment(Integer commentId);

}
