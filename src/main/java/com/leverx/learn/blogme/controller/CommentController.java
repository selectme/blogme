package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @GetMapping("/{postId}/comments")
//    public Comment addComment(@PathVariable Integer postId, @RequestBody Comment comment) {
//
////        return commentService.addComment(postId, comment);
//
//
//
//    }

    @GetMapping("/{postId}/comments")
    public List<Comment> addComment(@PathVariable Integer postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        return commentService.getComment(commentId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId){
        commentService.removeComment(commentId);
    }
}
