package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userDtoConverter.convertToEntity(userDto);
        String activationCode = codeService.generateActivationCode(user.getEmail());
        userService.addUser(user);
        mailService.send(user.getEmail(), "activation link", activationCode);

        return userDtoConverter.convertToDto(user);
    }


    @GetMapping("/{id}")
    @Transactional
    public UserDto getUser(@PathVariable Integer id) {
        return userDtoConverter.convertToDto(userService.getUserById(id));
    }
}
