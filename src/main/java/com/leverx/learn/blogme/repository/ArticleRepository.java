package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Viktar on 28.05.2020
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByTagsContains(Tag tag);

}
