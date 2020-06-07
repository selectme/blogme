package com.leverx.learn.blogme.dto.userdto;

import com.leverx.learn.blogme.entity.Article;

import java.util.Date;
import java.util.Set;

/**
 * @author Viktar on 31.05.2020
 */
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Date createdAt;

    private boolean isEnabled;

    private Set<Article> articles;

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @JsonIgnore
    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
