package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.commentDto.CommentDto;
import com.leverx.learn.blogme.dto.commentDto.CommentDtoConverter;
import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;
    private final UserService userService;
    private final ArticleService articleService;

    public CommentController(CommentService commentService, CommentDtoConverter commentDtoConverter, UserService userService, ArticleService articleService) {
        Assert.notNull(commentService, "commentService must not be null");
        Assert.notNull(commentDtoConverter, "commentDtoConverter must not be null");
        Assert.notNull(userService, "userService must not be null");
        Assert.notNull(articleService, "articleService must not be null");

        this.commentService = commentService;
        this.commentDtoConverter = commentDtoConverter;
        this.userService = userService;
        this.articleService = articleService;
    }


    @PostMapping("/{postId}/comments")
    public List<Comment> getAllCommentsOfArticle(@PathVariable Integer postId, Comment comment, Principal principal) {
        Assert.notNull(postId, "postId must not be null");
        Assert.notNull(comment, "comment must not be null");
        Assert.notNull(principal, "principal must not be null");

        Article article = articleService.getArticleById(postId);
        User author = userService.getUserByEmail(principal.getName());

        comment.setArticle(article);
        comment.setAuthor(author);

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getAllCommentsOfArticle(@PathVariable Integer postId) {
        Assert.notNull(postId, "postId must not be null");

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public CommentDto getComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        Assert.notNull(commentId, "commentId must not be null");

        return commentDtoConverter.convertToDto(commentService.getComment(commentId));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId, Principal principal) {
        Assert.notNull(commentId, "commentId must not be null");
        Assert.notNull(principal, "principal must not be null");

        Comment comment = commentService.getComment(commentId);
        User user = userService.getUserByEmail(principal.getName());
        Article article = articleService.getArticleById(postId);
        if (user.getId().equals(comment.getAuthor().getId()) || user.getId().equals(article.getAuthor().getId())){
            commentService.removeComment(commentId);
           return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
