package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.repository.CommentRepository;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Viktar on 08.06.2020
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final ArticleService articleService;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(ArticleService articleService, CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer articleId) {

        return articleService.getArticleById(articleId).getComments();
    }

    @Override
    //todo how to set right author?
    public Comment addComment(Integer postId, Comment comment) {

        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Integer commentId) {
       return commentRepository.getOne(commentId);
    }

    @Override
    public void removeComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}
