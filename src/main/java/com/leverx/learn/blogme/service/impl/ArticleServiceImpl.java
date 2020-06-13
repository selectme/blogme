package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Tag;
import com.leverx.learn.blogme.repository.ArticleRepository;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 28.05.2020
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String ARTICLE_NOT_EMPTY = "Article must not be empty";
    private static final String ID_NOT_EMPTY = "Id must not be empty";
   private static final String TAGS_NOT_EMPTY = "Tags must not be empty";

    private final ArticleRepository articleRepository;
    private final TagService tagService;


    public ArticleServiceImpl(ArticleRepository articleRepository, TagService tagService) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public Article addArticle(Article article) {
        Assert.notNull(article, ARTICLE_NOT_EMPTY);

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
        Assert.notNull(id, ID_NOT_EMPTY);

        return articleRepository.getOne(id);
    }

    @Override
    public void deleteArticle(Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        articleRepository.deleteById(id);
    }

    @Override
    public Article editArticle(Article article) {
        Assert.notNull(article, ARTICLE_NOT_EMPTY);

        Article editedArticle = new Article();
        editedArticle.setId(article.getId());
        editedArticle.setTitle(article.getTitle());
        editedArticle.setText(article.getText());
        editedArticle.setStatus(article.getStatus());
        editedArticle.setUpdatedAt(new Date());
        return articleRepository.save(editedArticle);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Set<Article> findAllArticlesByTags(List<String> tags) {
        Assert.notEmpty(tags, TAGS_NOT_EMPTY);

        Set<Article> articles = new HashSet<>();

        if (!tags.isEmpty()) {
            for (String tagName : tags) {
                Tag tag = tagService.findByName(tagName);
                List<Article> foundArticles = articleRepository.findByTagsContains(tag);
                if (!foundArticles.isEmpty()) {
                    articles.addAll(foundArticles);
                }
            }
        }
        return articles;
    }
}
