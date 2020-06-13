package com.leverx.learn.blogme.dto.articledto;

import com.leverx.learn.blogme.ArticleStatus;
import com.leverx.learn.blogme.entity.User;

import java.util.Date;

/**
 * @author Viktar on 05.06.2020
 */
public class ArticleDto {

    private Integer id;

    private String title;

    private String text;

    private ArticleStatus status;

    private User author;

    private Date createdAt;

    private Date updatedAt;



    public ArticleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
