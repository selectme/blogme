package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Viktar on 28.05.2020
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
