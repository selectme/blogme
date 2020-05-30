package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Viktar on 28.05.2020
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article editArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
