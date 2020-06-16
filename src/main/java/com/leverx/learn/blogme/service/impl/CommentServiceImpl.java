package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.repository.CommentRepository;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Viktar on 08.06.2020
 *
 * Implementation of {@link CommentService}
 *
 * @see CommentService
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final ArticleService articleService;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(ArticleService articleService, CommentRepository commentRepository, ArticleRepository articleRepository) {
        Assert.notNull(articleService, "articleService must not be null");
        Assert.notNull(commentRepository, "commentRepository must not be null");
        Assert.notNull(articleRepository, "articleRepository must not be null");

        this.articleService = articleService;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer articleId) {
        Assert.notNull(articleId, "articleId must not be null");

        return articleService.getArticleById(articleId).getComments();
    }

    @Override
    //todo how to set right author?
    public Comment addComment(Integer postId, Comment comment) {

        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Integer commentId) {
        Assert.notNull(commentId, "commentId must not be null");

        return commentRepository.getOne(commentId);
    }

    @Override
    public void removeComment(Integer commentId) {
        Assert.notNull(commentId, "commentId must not be null");

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> findByArticleIdAndAuthorId(Integer articleId, Integer authorId, Pageable pageable) {
        Slice<Comment> commentSlice = commentRepository.findByArticleIdAndAuthorId(articleId, authorId, pageable);
        return commentSlice.getContent();
    }
}


