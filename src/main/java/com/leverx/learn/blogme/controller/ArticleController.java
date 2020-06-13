package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.articledto.ArticleDto;
import com.leverx.learn.blogme.dto.articledto.ArticleDtoConverter;
import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private static final String ARTICLE_SERVICE_NOT_EMPTY = "articleService must not be null";
    private static final String TAG_SERVICE_NOT_EMPTY = "tagService must not be null";
    private static final String ARTICLE_DTO_CONVERTER_NOT_EMPTY = "articleDtoConverter must not be null";
    private static final String COMMENT_SERVICE_NOT_EMPTY = "commentService must not be null";

    private final ArticleService articleService;
    private final TagService tagService;
    private final ArticleDtoConverter articleDtoConverter;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, TagService tagService, ArticleDtoConverter articleDtoConverter, CommentService commentService) {
        this.commentService = commentService;
        Assert.notNull(articleService, ARTICLE_SERVICE_NOT_EMPTY);
        Assert.notNull(tagService, TAG_SERVICE_NOT_EMPTY);
        Assert.notNull(articleDtoConverter, ARTICLE_DTO_CONVERTER_NOT_EMPTY);
        Assert.notNull(commentService, COMMENT_SERVICE_NOT_EMPTY);

        this.articleService = articleService;
        this.tagService = tagService;
        this.articleDtoConverter = articleDtoConverter;
    }


    @PostMapping
    @ResponseBody
    public ArticleDto addArticle(@RequestBody Article article) {
        Assert.notNull(article, ARTICLE_NOT_EMPTY);

        return articleDtoConverter.convertToDto(articleService.addArticle(article));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ArticleDto getArticle(@PathVariable Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        return articleDtoConverter.convertToDto(articleService.getArticleById(id));
    }

    @PutMapping("/{id}")
    public ArticleDto editArticle(@RequestBody Article editedArticle, @PathVariable Integer id) {
        Assert.notNull(editedArticle, ARTICLE_NOT_EMPTY);
        Assert.notNull(id, ID_NOT_EMPTY);

        return articleDtoConverter.convertToDto(articleService.editArticle(editedArticle));
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

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping
    List<Article> getFilteredAndSortedArticles(
            @RequestParam(value = "skip") Integer pageNo,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "q") String postTitle,
            @RequestParam(value = "author") Integer authorId,
            @RequestParam(value = "sort") String sortBy,
            @RequestParam(value = "order") String order) {

        Sort sort = Sort.by(sortBy).descending();
        if(order.equals("asc")){
            sort.ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return articleService.findByTitleAndAndAuthorId(postTitle, authorId, pageable);
    }

    @GetMapping("/{id}/comments")
    List<Comment> getFilteredAndSortedComments(
            @PathVariable Integer id,
            @RequestParam(value = "skip") Integer pageNo,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "q") String postTitle,
            @RequestParam(value = "author") Integer authorId,
            @RequestParam(value = "sort") String sortBy,
            @RequestParam(value = "order") String order) {

        Sort sort = Sort.by(sortBy).descending();
        if(order.equals("asc")){
            sort.ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return commentService.findByTitleAndAndAuthorId(postTitle, authorId, pageable);
    }


}
