package com.leverx.learn.blogme.dto.commentDto;

import java.util.Date;

/**
 * @author Viktar on 12.06.2020
 *
 * Model data transfer object of {@link com.leverx.learn.blogme.entity.Comment}
 */
public class CommentDto {

    private Integer id;
    private String text;
    private Integer authorId;
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
