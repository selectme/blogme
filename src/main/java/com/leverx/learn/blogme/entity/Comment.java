package com.leverx.learn.blogme.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Viktar on 27.05.2020
 */
@Entity
@Table(name = "comments")
@Proxy(lazy = false)
public class Comment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(name = "text", columnDefinition = "text")
    private String text;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    public Comment() {
    }

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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
