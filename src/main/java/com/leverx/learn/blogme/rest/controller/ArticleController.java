package com.leverx.learn.blogme.rest.controller;

import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.rest.dto.articledto.ArticleDto;
import com.leverx.learn.blogme.rest.dto.articledto.ArticleDtoConverter;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import com.leverx.learn.blogme.service.TagService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * REST controller for {@link Article}.
 *
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {


    private final ArticleService articleService;
    private final TagService tagService;
    private final ArticleDtoConverter articleDtoConverter;
    private final CommentService commentService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, TagService tagService, ArticleDtoConverter articleDtoConverter, CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
        Assert.notNull(articleService, "articleService must not be null");
        Assert.notNull(tagService, "tagService must not be null");
        Assert.notNull(articleDtoConverter, "articleDtoConverter must not be null");
        Assert.notNull(commentService, "commentService must not be null");
        Assert.notNull(userService, "userService must not be null");

        this.articleService = articleService;
        this.tagService = tagService;
        this.articleDtoConverter = articleDtoConverter;
    }

    @PostMapping
    @ResponseBody
    public ArticleDto addArticle(@RequestBody Article article, Principal principal) {
        Assert.notNull(article, "article must not be null");

        User user = userService.getUserByEmail(principal.getName());
        article.setAuthor(user);
        return articleDtoConverter.convertToDto(articleService.addArticle(article));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ArticleDto getArticle(@PathVariable Integer id) {
        Assert.notNull(id, "id must not be null");

        return articleDtoConverter.convertToDto(articleService.getArticleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity editArticle(@RequestBody Article editedArticle, @PathVariable Integer id, Principal principal) {
        Assert.notNull(editedArticle, "editedArticle must not be null");
        Assert.notNull(id, "id must not be null");

        String userEmail = principal.getName();
        User user = userService.getUserByEmail(userEmail);
        if (user.getId().equals(editedArticle.getAuthor().getId())) {
            return new ResponseEntity(articleDtoConverter.convertToDto(articleService.editArticle(editedArticle)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable Integer id, Principal principal) {
        Assert.notNull(id, "id must not be null");

        User user = userService.getUserByEmail(principal.getName());
        Article article = articleService.getArticleById(id);
        if (user.getId().equals(article.getAuthor().getId())) {
            articleService.deleteArticle(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/my")
    public ResponseEntity getAuthorsArticles(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());

        return new ResponseEntity(user.getArticles(), HttpStatus.OK);
    }

    @GetMapping(params = "tags")
    public Set<Article> getArticlesByTags(@RequestParam("tags") List<String> tags) {
        Assert.notEmpty(tags, "tags must not be null");

        return articleService.findAllArticlesByTags(tags);
    }

    @GetMapping()
    public List<Article> getArticles(
            @RequestParam("skip") Integer pageNo,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("q") String postTitle,
            @RequestParam("author") Integer authorId,
            @RequestParam("sort") String sortBy,
            @RequestParam("order") String order) {
        Assert.notNull(pageNo, "pageNo must not be null");
        Assert.notNull(pageSize, "pageSize must not be null");
        Assert.hasText(postTitle, "postTitle must not be empty");
        Assert.notNull(authorId, "authorId must not be null");
        Assert.hasText(sortBy, "sort must not be empty");
        Assert.hasText(order, "order must not be empty");

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, order);
        List<Article> byTitleAndAuthorId = articleService.findByTitleAndAuthorId(postTitle, authorId, pageable);
        return byTitleAndAuthorId;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(
            @PathVariable Integer id,
            @RequestParam("skip") Integer pageNo,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("author") Integer authorId,
            @RequestParam("sort") String sortBy,
            @RequestParam("order") String order) {
        Assert.notNull(pageNo, "pageNo must not be null");
        Assert.notNull(pageSize, "pageSize must not be null");
        Assert.notNull(authorId, "authorId must not be null");
        Assert.hasText(sortBy, "sort must not be empty");
        Assert.hasText(order, "order must not be empty");

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, order);
        return commentService.findByArticleIdAndAuthorId(id, authorId, pageable);
    }

    private Pageable getPageable(Integer pageNo, Integer pageSize, String sortBy, String order) {
        Sort sort = Sort.by(sortBy).by(Sort.Direction.fromString(order));
        return PageRequest.of(pageNo, pageSize, sort);
    }
}
