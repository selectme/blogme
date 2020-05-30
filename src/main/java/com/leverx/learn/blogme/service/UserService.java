package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.User;

import java.util.List;

/**
 * @author Viktar on 27.05.2020
 */
public interface UserService {

    User addUser(User user);

    User getUserById(Integer id);

    void deleteUser(Integer id);

    User editUser(User user);

    List<User> getAllUsers();

}
