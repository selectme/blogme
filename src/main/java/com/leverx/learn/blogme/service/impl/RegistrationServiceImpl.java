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
 *
 * Implementation of {@link RegistrationService}
 *
 * @see RegistrationService
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final String EMAIL_SUBJECT = "Activation link";
    private static final String EMAIL_ACTIVATION_MESSAGE = "http://localhost:8080/auth/confirm/";

    private final UserService userService;
    private final ActivationCodeService codeService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserService userService, ActivationCodeService codeService,
                                   MailService mailService, PasswordEncoder passwordEncoder) {
        Assert.notNull(userService, "userService must not be null");
        Assert.notNull(codeService, "codeService must not be null");
        Assert.notNull(mailService, "mailService must not be null");
        Assert.notNull(passwordEncoder, "passwordEncoder must not be null");

        this.userService = userService;
        this.codeService = codeService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        Assert.notNull(user, "user must not be null");

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