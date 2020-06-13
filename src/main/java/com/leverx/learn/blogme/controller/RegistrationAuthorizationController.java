package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.authenticationDto.AuthenticationRequestDto;
import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author Viktar on 07.06.2020
 */
@RestController
@RequestMapping("/auth")
public class RegistrationAuthorizationController {

    private static final String USER_NOT_EMPTY = "User must not be empty";
    private static final String EMAIL_NOT_EMPTY = "Email must not be empty";
    private static final String ACTIVATION_CODE_NOT_EMPTY = "Activation code must not be empty";
    private static final String CODE_NEW_PASSWORD_NOT_EMPTY = "Code and password must not be empty";

    private static final String FORGOT_PASSWORD_SUBJECT = "Instruction for forgotten password";
    private static final String FORGOT_PASSWORD_MESSAGE = "User this code for reset password ";
    private static final String ACTIVATION_CODE_VALID = "Your activation code is valid";
    private static final String ACTIVATION_CODE_NOT_VALID = "Your activation code is not valid";
    private static final String LOGIN_PASSWORD_NOT_EMPTY = "Login and password must not be empty";

    private static final String USER_SERVICE_NOT_EMPTY = "userService must not be null";
    private static final String USER_DTO_CONVERTER_NOT_EMPTY = "userDtoConverter must not be null";
    private static final String REGISTRATION_SERVICE_NOT_EMPTY = "registrationService must not be null";
    private static final String CODE_SERVICE_NOT_EMPTY = "activationCodeService must not be null";
    private static final String MAIL_SERVICE_NOT_EMPTY = "mailService must not be null";
    private static final String PASSWORD_ENCODER_NOT_EMPTY = "passwordEncoder must not be null";
    private static final String AUTHORIZATION_SERVICE_NOT_EMPTY = "authorizationService must not be null";



    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final RegistrationService registrationService;
    private final ActivationCodeService codeService;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthorizationService authorizationService;


    public RegistrationAuthorizationController(UserService userService, UserDtoConverter userDtoConverter,
                                               RegistrationService registrationService, ActivationCodeService codeService,
                                               MailService mailService, BCryptPasswordEncoder passwordEncoder,
                                               AuthorizationService authorizationService) {
        Assert.notNull(userService, USER_SERVICE_NOT_EMPTY);
        Assert.notNull(userDtoConverter, USER_DTO_CONVERTER_NOT_EMPTY);
        Assert.notNull(registrationService, REGISTRATION_SERVICE_NOT_EMPTY);
        Assert.notNull(codeService, CODE_SERVICE_NOT_EMPTY);
        Assert.notNull(mailService, MAIL_SERVICE_NOT_EMPTY);
        Assert.notNull(passwordEncoder, PASSWORD_ENCODER_NOT_EMPTY);
        Assert.notNull(authorizationService, AUTHORIZATION_SERVICE_NOT_EMPTY);

        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.registrationService = registrationService;
        this.codeService = codeService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;

        this.authorizationService = authorizationService;
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody User user) {
        Assert.notNull(user, USER_NOT_EMPTY);

        return userDtoConverter.convertToDto(registrationService.registerUser(user));
    }

    @GetMapping("/confirm/{activationCode}")
    public void confirmRegistration(@PathVariable String activationCode) {
        Assert.notNull(activationCode, ACTIVATION_CODE_NOT_EMPTY);

        codeService.activateUserByCode(activationCode);
    }

    @PostMapping("/forgot_password")
    public void forgotPassword(@RequestBody String email) {
        Assert.notNull(email, EMAIL_NOT_EMPTY);

        String code = codeService.generateActivationCode(email);
        mailService.send(email, FORGOT_PASSWORD_SUBJECT, FORGOT_PASSWORD_MESSAGE + code);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody CodeAndPasswordHolder holder) {
        Assert.notNull(holder, CODE_NEW_PASSWORD_NOT_EMPTY);

        String code = holder.code;
        String password = holder.password;
        codeService.resetPassword(code, passwordEncoder.encode(password));
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestBody String activationCode) {
        Assert.notNull(activationCode, ACTIVATION_CODE_NOT_EMPTY);

        if (codeService.isCodeExists(activationCode)) {
            return new ResponseEntity<>(ACTIVATION_CODE_VALID, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ACTIVATION_CODE_NOT_VALID, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        Assert.notNull(requestDto, LOGIN_PASSWORD_NOT_EMPTY);

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        return ResponseEntity.ok(authorizationService.login(email, password));
    }

    @Component
    public static class CodeAndPasswordHolder {
        private String code;
        private String password;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
