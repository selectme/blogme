package com.leverx.learn.blogme.service;

import java.util.Map;

/**
 * @author Viktar on 12.06.2020
 */
public interface AuthorizationService {

    /**
     * Allows to user {@link com.leverx.learn.blogme.entity.User} to log in to be able add, delete,
     * edit articles{@link com.leverx.learn.blogme.entity.Article}, commentaries{@link com.leverx.learn.blogme.entity.Comment}
     *
     * @param email email for log in
     * @param password password for log in
     * @return Map which contains email and token for authentication
     */
    Map<Object, Object> login(String email, String password);
}
