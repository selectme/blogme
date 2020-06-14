package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.repository.UserRepository;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Viktar on 28.05.2020
 *
 * Implementation of {@link UserService}
 *
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {
        Assert.notNull(user, "user must not be null");

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        Assert.notNull(id, "id must not be null");

        return userRepository.getOne(id);
    }

    @Override
    public User getUserByEmail(String email) {
        Assert.hasText(email, "email must not be empty");

        return userRepository.findByEmail(email);
    }

}
