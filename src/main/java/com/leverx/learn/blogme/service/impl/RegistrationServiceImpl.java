package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.RegistrationService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Viktar on 07.06.2020
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final String EMAIL_SUBJECT = "Activation link";
    private static final String EMAIL_ACTIVATION_MESSAGE = "http://localhost:8080/auth/confirm/";
    private static final String USER_NOT_EMPTY = "User must not be empty";
    private static final String CONFIRMATION_CODE_NOT_EMPTY = "Confirmation code must not be empty";
    private static final String CODE_SERVICE_NOT_EMPTY = "activationCode code must not be null";
    private static final String USER_SERVICE_NOT_EMPTY = "userService code must not be null";
    private static final String MAIL_SERVICE_NOT_EMPTY = "mailService code must not be null";
    private static final String PASSWORD_ENCODER_NOT_EMPTY = "passwordEncoder must not be null";


    private final UserService userService;
    private final ActivationCodeService codeService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserService userService, ActivationCodeService codeService,
                                   MailService mailService, PasswordEncoder passwordEncoder) {
        Assert.notNull(userService, USER_SERVICE_NOT_EMPTY);
        Assert.notNull(codeService, CODE_SERVICE_NOT_EMPTY);
        Assert.notNull(mailService, MAIL_SERVICE_NOT_EMPTY);
        Assert.notNull(passwordEncoder, PASSWORD_ENCODER_NOT_EMPTY);

        this.userService = userService;
        this.codeService = codeService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        Assert.notNull(user, USER_NOT_EMPTY);

        if (userService.getUserByEmail(user.getEmail()) == null) {
            codeService.generateActivationCode(user.getEmail());
            String activationCode = codeService.generateActivationCode(user.getEmail());
            mailService.send(user.getEmail(), EMAIL_SUBJECT, EMAIL_ACTIVATION_MESSAGE + activationCode);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(new Date());
            userService.addUser(user);
            return user;
        } else {
            throw new IllegalStateException("Email " + user.getEmail() + " has already registered");
        }
    }


}
