package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Article;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
public interface ArticleService {

    Article addArticle(Article article);

    Article getArticleById(Integer id);

    void deleteArticle(Integer id);

    Article editArticle(Article article);

    List<Article> getAllArticles();
}
