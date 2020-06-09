package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Viktar on 08.06.2020
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
