package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.repository.CommentRepository;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Viktar on 08.06.2020
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final String ARTICLE_ID_NOT_EMPTY = "Article id must not be empty";
    private static final String COMMENT_ID_NOT_EMPTY = "Id must not be empty";
    private static final String ARTICLE_SERVICE_NOT_EMPTY = "articleService must not be null";
    private static final String COMMENT_REPOSITORY_NOT_EMPTY = "commentRepository must not be null";
    private static final String ARTICLE_REPOSITORY_NOT_EMPTY = "articleRepository must not be null";

    private final ArticleService articleService;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(ArticleService articleService, CommentRepository commentRepository, ArticleRepository articleRepository) {
        Assert.notNull(articleService, ARTICLE_SERVICE_NOT_EMPTY);
        Assert.notNull(commentRepository, COMMENT_REPOSITORY_NOT_EMPTY);
        Assert.notNull(articleRepository, ARTICLE_REPOSITORY_NOT_EMPTY);

        this.articleService = articleService;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer articleId) {
        Assert.notNull(articleId, ARTICLE_ID_NOT_EMPTY);

        return articleService.getArticleById(articleId).getComments();
    }

    @Override
    //todo how to set right author?
    public Comment addComment(Integer postId, Comment comment) {

        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Integer commentId) {
        Assert.notNull(commentId, COMMENT_ID_NOT_EMPTY);

        return commentRepository.getOne(commentId);
    }

    @Override
    public void removeComment(Integer commentId) {
        Assert.notNull(commentId, COMMENT_ID_NOT_EMPTY);

        commentRepository.deleteById(commentId);
    }
}
