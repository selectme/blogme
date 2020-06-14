package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.articledto.ArticleDto;
import com.leverx.learn.blogme.dto.articledto.ArticleDtoConverter;
import com.leverx.learn.blogme.entity.Article;
import com.leverx.learn.blogme.entity.ArticleStatus;
import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.ArticleService;
import com.leverx.learn.blogme.service.CommentService;
import com.leverx.learn.blogme.service.TagService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            @Nullable @RequestParam("skip") Integer pageNo,
            @Nullable @RequestParam("limit") Integer pageSize,
            @Nullable @RequestParam("q") String postTitle,
            @Nullable @RequestParam("author") Integer authorId,
            @Nullable @RequestParam("sort") String sortBy,
            @Nullable @RequestParam("order") String order) {

        if (pageNo == null && pageSize == null && postTitle == null && authorId == null && sortBy == null && order == null) {
            List<Article> publicArticles = articleService.getAllArticles().stream()
                    .filter(article -> article.getStatus().equals(ArticleStatus.PUBLIC))
                    .collect(Collectors.toList());
            return publicArticles;
        }
        Pageable pageable = getPageable(pageNo, pageSize, sortBy, order);
        return articleService.findByTitleAndAuthorId(postTitle, authorId, pageable);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getFilteredAndSortedComments(
            @PathVariable Integer id,
            @Nullable @RequestParam("skip") Integer pageNo,
            @Nullable @RequestParam("limit") Integer pageSize,
            @Nullable @RequestParam("author") Integer authorId,
            @Nullable @RequestParam("sort") String sortBy,
            @Nullable @RequestParam("order") String order) {

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, order);
        return commentService.findByArticleIdAndAuthorId(id, authorId, pageable);
    }

    private Pageable getPageable(@RequestParam("skip") Integer pageNo, @RequestParam("limit") Integer pageSize,
                                 @RequestParam("sort") String sortBy, @RequestParam("order") String order) {
        Sort sort = Sort.by(sortBy).by(Sort.Direction.fromString(order));
        return PageRequest.of(pageNo, pageSize, sort);
    }
}
