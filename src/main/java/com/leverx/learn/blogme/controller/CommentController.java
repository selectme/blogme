package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.service.CommentService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class CommentController {

    private static final String ARTICLE_ID_NOT_EMPTY = "Article id must not be empty";
    private static final String COMMENT_ID_NOT_EMPTY = "Id must not be empty";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/{postId}/comments")
    public List<Comment> addComment(@PathVariable Integer postId) {
        Assert.notNull(postId, ARTICLE_ID_NOT_EMPTY);
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        Assert.notNull(commentId, COMMENT_ID_NOT_EMPTY);
        return commentService.getComment(commentId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId){
        Assert.notNull(commentId, COMMENT_ID_NOT_EMPTY);
        commentService.removeComment(commentId);
    }
}
