package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Viktar on 08.06.2020
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>, PagingAndSortingRepository<Comment, Integer> {

    Slice<Comment> findByArticleIdAndAuthorId(Integer articleId, Integer authorId, Pageable pageable);
}
