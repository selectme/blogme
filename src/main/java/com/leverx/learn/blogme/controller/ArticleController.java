package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static final String ID_NOT_EMPTY = "Id must not be empty";
    private static final String ARTICLE_NOT_EMPTY = "Article must not be empty";
    private static final String TAG_NOT_EMPTY = "Tags must not be empty";

    private final ArticleService articleService;
    private final TagService tagService;

    public ArticleController(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }


    @PostMapping
    @ResponseBody
    public Article addArticle(@RequestBody Article article) {
        Assert.notNull(article, ARTICLE_NOT_EMPTY);

        return articleService.addArticle(article);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        return articleService.getArticleById(id);
    }

    @PutMapping("/{id}")
    public Article editArticle(@RequestBody Article editedArticle, @PathVariable Integer id) {
        Assert.notNull(editedArticle, ARTICLE_NOT_EMPTY);
        Assert.notNull(id, ID_NOT_EMPTY);

        return articleService.editArticle(editedArticle);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        articleService.deleteArticle(id);
    }

    @GetMapping(params = "tags")
    public Set<Article> getArticlesByTags(@RequestParam(value = "tags") List<String> tags) {
        Assert.notEmpty(tags, TAG_NOT_EMPTY);
        return articleService.findAllArticlesByTags(tags);
    }

    @GetMapping()
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
}
