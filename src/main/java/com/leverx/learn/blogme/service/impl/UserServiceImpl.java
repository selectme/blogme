package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.repository.UserRepository;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Viktar on 28.05.2020
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_EMPTY = "User must not be empty";
    private static final String ID_NOT_EMPTY = "Id must not be empty";
    private static final String EMAIL_NOT_EMPTY = "Email must not be empty";
    private static final String USER_REPOSITORY_NOT_EMPTY = "userRepository must not be null";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        Assert.notNull(userRepository, USER_REPOSITORY_NOT_EMPTY);

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {
        Assert.notNull(user, USER_NOT_EMPTY);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        userRepository.deleteById(id);
    }

    @Override
    public User editUser(User user) {
        Assert.notNull(user, USER_NOT_EMPTY);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        Assert.notNull(email, EMAIL_NOT_EMPTY);

        return userRepository.findByEmail(email);
    }

}
