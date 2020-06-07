package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Tag;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 28.05.2020
 */
@Service
public class ArticleServiceImpl implements ArticleService {


    private ArticleRepository articleRepository;

    private TagService tagService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, TagService tagService) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
    }

    @Override
    //todo article dto
    @Transactional
    public Article addArticle(Article article) {
        Set<Tag> postTags = article.getTags();
        if (postTags != null) {
            Set<Tag> tagsToBeSaved = new HashSet<>();
            for (Tag postTag : postTags) {
                Tag tag = tagService.findByName(postTag.getName());
                if (tag != null) {
                    tagsToBeSaved.add(tag);
                } else {
                    Tag newTag = new Tag();
                    newTag.setName(postTag.getName());
                    tagsToBeSaved.add(newTag);
                }
            }
            article.setTags(tagsToBeSaved);
        }

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
    //todo asserts
    public Article editArticle(Article article) {
        Article editedArticle = new Article();
        editedArticle.setId(article.getId());
        editedArticle.setTitle(article.getTitle());
        editedArticle.setText(article.getText());
        editedArticle.setStatus(article.getStatus());
        editedArticle.setUpdatedAt(article.getUpdatedAt());
        return articleRepository.save(editedArticle);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    //todo List<String> tags to Set
    public Set<Article> findAllArticlesByTags(List<String> tags) {

        Assert.notEmpty(tags, "tags must not be empty");

        Set<Article> articles = new HashSet<>();

        if (!tags.isEmpty()) {
            for (String tagName : tags) {
                Tag tag = tagService.findByName(tagName);
                List<Article> foundArticles = articleRepository.findByTagsContains(tag);
                if(!foundArticles.isEmpty()){
                    articles.addAll(foundArticles);
                }
            }
        }
        return articles;
    }
}
