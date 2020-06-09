package com.leverx.learn.blogme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.learn.blogme.ArticleStatus;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Viktar on 27.05.2020
 */
@Entity
@Table(name = "posts")
@Proxy(lazy = false)
public class Article {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum")
    private ArticleStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    @JsonIgnore
    private List<Comment> comments;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "tags_to_posts", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;


    public Article() {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
