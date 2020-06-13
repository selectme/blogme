package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.User;

/**
 * @author Viktar on 27.05.2020
 *
 * Provides methods for working with {@link User}
 */
public interface UserService {

    /**
     * Allows to add {@link User}
     *
     * @param user {@link User} which will be added.
     * @return {@link User}
     */
    User addUser(User user);

    /**
     * Allows to get {@link User} using its id.
     *
     * @param id of {@link User}
     * @return {@link User}
     */
    User getUserById(Integer id);

    /**
     * Allows to get {@link User} using its email.
     *
     * @param email of {@link User}
     * @return {@link User}
     */
    User getUserByEmail(String email);

}
