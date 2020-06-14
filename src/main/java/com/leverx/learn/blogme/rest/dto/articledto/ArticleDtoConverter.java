package com.leverx.learn.blogme.rest.dto.articledto;

import com.leverx.learn.blogme.entity.Article;
import org.springframework.stereotype.Component;

/**
 * Allows to convert {@link Article} into {@link ArticleDto} and vice versa.
 *
 * @author Viktar on 05.06.2020
 */
@Component
public class ArticleDtoConverter {

    public ArticleDto convertToDto(Article article) {

        ArticleDto articleDto = new ArticleDto();

        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setText(article.getText());
        articleDto.setAuthor(article.getAuthor());
        articleDto.setCreatedAt(article.getCreatedAt());
        articleDto.setUpdatedAt(article.getUpdatedAt());

        return articleDto;
    }

    public Article convertToEntity(ArticleDto articleDto) {

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setAuthor(articleDto.getAuthor());
        article.setCreatedAt(articleDto.getCreatedAt());
        article.setUpdatedAt(articleDto.getUpdatedAt());

        return article;
    }
}
