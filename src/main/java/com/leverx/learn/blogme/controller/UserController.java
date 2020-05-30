package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseBody
    public User createUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        User userById = userService.getUserById(id);
        return userById;

    }
}
