package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.util.Assert;
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

    private static final String ID_NOT_EMPTY = "Id must not be empty";
    private static final String USER_SERVICE_NOT_EMPTY = "userService must not be empty";
    private static final String USER_DTO_CONVERTER_NOT_EMPTY = "userDtoConverter must not be empty";
    private static final String CODE_SERVICE_NOT_EMPTY = "codeService must not be empty";
    private static final String MAIL_SERVICE_NOT_EMPTY = "mailService must not be empty";

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final ActivationCodeService codeService;
    private final MailService mailService;

    public UserController(UserService userService, UserDtoConverter userDtoConverter,
                          ActivationCodeService codeService, MailService mailService) {
        Assert.notNull(userService, USER_SERVICE_NOT_EMPTY);
        Assert.notNull(userDtoConverter, USER_DTO_CONVERTER_NOT_EMPTY);
        Assert.notNull(codeService, CODE_SERVICE_NOT_EMPTY);
        Assert.notNull(mailService, MAIL_SERVICE_NOT_EMPTY);

        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.codeService = codeService;
        this.mailService = mailService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        Assert.notNull(id, ID_NOT_EMPTY);

        return userDtoConverter.convertToDto(userService.getUserById(id));
    }
}
