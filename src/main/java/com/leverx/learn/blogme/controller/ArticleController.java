package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {


    private ArticleService articleService;
    private TagService tagService;

    @Autowired
    public ArticleController(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseBody
    public Article addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    @PutMapping("/{id}")
    public Article editArticle(@RequestBody Article editedArticle, @PathVariable Integer id) {
        return articleService.editArticle(editedArticle);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
    }

    @GetMapping(params = "tags")
    public Set<Article> getArticlesByTags(@RequestParam(value = "tags") List<String> tags) {
        return articleService.findAllArticlesByTags(tags);
    }

    @GetMapping()
    public List<Article> getAllArticles(){
        return articleService.getAllArticles();
    }


}
