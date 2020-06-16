package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 27.05.2020
 *
 * Provides methods for work with articles {@link Article}
 */
public interface ArticleService {

    /**
     * Allows to add an article {@link Article}
     *
     * @param article {@link Article} which will be added.
     * @return {@link Article}
     */
    Article addArticle(Article article);

    /**
     * Allows to find an article {@link Article} using its id.
     *
     * @param id {@link Article} id
     * @return {@link Article}
     */
    Article getArticleById(Integer id);

    /**
     * Allows to delete an article {@link Article} using its id.
     * @param id {@link Article} id
     */
    void deleteArticle(Integer id);

    /**
     * Allows to edit an article {@link Article}.
     *
     * @param article edited {@link Article}.
     * @return {@link Article}
     */
    Article editArticle(Article article);

    /**
     * Allows to get list of all articles {@link Article}
     *
     * @return list of {@link Article}
     */
    List<Article> getAllArticles();

    /**
     * Allows to find all articles {@link Article} with given tags {@link com.leverx.learn.blogme.entity.Tag}
     *
     * @param tagNames list of {@link com.leverx.learn.blogme.entity.Tag}
     * @return set of {@link Article} with givet {@link com.leverx.learn.blogme.entity.Tag}
     */
    Set<Article> findAllArticlesByTags(List<String> tagNames);

    /**
     * Allows to find all {@link Article} using its title and author{@link com.leverx.learn.blogme.entity.User} id
     * as well as conditions of pagination and sorting.
     *
     * @param postTitle of {@link Article}
     * @param authorId of {@link Article}
     * @param pageable condition of the pagination.
     * @return list of {@link Article} satisfying the condition.
     */
    List<Article> findByTitleAndAuthorId(String postTitle, Integer authorId, Pageable pageable);
}
