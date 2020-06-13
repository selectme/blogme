package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 *
 * Provides methods for working with commentaries {@link Comment}
 */
public interface CommentService {

    /**
     * Allows to find all {@link Comment} of the {@link com.leverx.learn.blogme.entity.Article}
     *
     * @param articleId of the {@link com.leverx.learn.blogme.entity.Article}
     * @return list of {@link Comment}
     */
    List<Comment> getCommentsByPostId(Integer articleId);

    /**
     * Allows to add {@link Comment} to the {@link com.leverx.learn.blogme.entity.Article}
     *
     * @param postId of the {@link com.leverx.learn.blogme.entity.Article}
     * @param comment {@link Comment} which will be added to the {@link com.leverx.learn.blogme.entity.Article}
     * @return {@link Comment} which was added.
     */
    Comment addComment(Integer postId, Comment comment);

    /**
     * Allows to find {@link Comment} using its id.
     * @param commentId id for searching.
     * @return {@link Comment}
     */
    Comment getComment(Integer commentId);

    /**
     * Allows to delete {@link Comment} using its id.
     * @param commentId id for deleting {@link Comment}
     */
    void removeComment(Integer commentId);

    /**
     * Allows to find all {@link Comment} using title of {@link com.leverx.learn.blogme.entity.Article}
     * and author {@link com.leverx.learn.blogme.entity.User} id as well as conditions of pagination and sorting.
     *
     * @param postTitle title of {@link com.leverx.learn.blogme.entity.Article}
     * @param authorId id of {@link com.leverx.learn.blogme.entity.User}
     * @param pageable condition of the pagination.
     * @return  list of {@link Comment} satisfying the condition.
     */
    List<Comment> findByTitleAndAndAuthorId(String postTitle, Integer authorId, Pageable pageable);

}
