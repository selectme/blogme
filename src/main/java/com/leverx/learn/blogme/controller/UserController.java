package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final ActivationCodeService codeService;
    private final MailService mailService;

    public UserController(UserService userService, UserDtoConverter userDtoConverter, ActivationCodeService codeService, MailService mailService) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.codeService = codeService;
        this.mailService = mailService;
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userDtoConverter.convertToDto(userService.getUserById(id));
    }
}
