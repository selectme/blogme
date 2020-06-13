package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.User;

/**
 * @author Viktar on 07.06.2020
 *
 * Provides methods for register users {@link User}
 */
public interface RegistrationService {

    /**
     * Allows to register users {@link User}
     *
     * @param user for registering.
     * @return user {@link User} which was registered.
     */
    User registerUser(User user);

}
