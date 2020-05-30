package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

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
}
