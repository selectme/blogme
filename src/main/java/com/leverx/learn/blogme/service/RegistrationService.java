package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.User;

/**
 * @author Viktar on 07.06.2020
 */
public interface RegistrationService {

    User registerUser(User user);

    String activateUserByConfirmationCode(String confirmationCode);
}
