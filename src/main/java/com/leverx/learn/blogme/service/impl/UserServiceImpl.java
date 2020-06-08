package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.repository.UserRepository;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Viktar on 28.05.2020
 */
@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changePassword(User user, String newPassword){

    }
}
