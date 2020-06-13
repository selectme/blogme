package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.AuthorizationService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

//import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;

/**
 * @author Viktar on 12.06.2020
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String LOGIN_PASSWORD_NOT_EMPTY = "Login and password must not be empty";
    private static final String USER_SERVICE_NOT_EMPTY = "userService must not be null";
    private static final String AUTHENTICATION_MANAGER_NOT_EMPTY = "authenticationManager must not be null";
    private static final String JWT_PROVIDER_NOT_EMPTY = "jwtTokenProvider must not be null";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
//    private final JwtTokenProvider jwtTokenProvider;

//    public AuthorizationServiceImpl(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
//        Assert.notNull(authenticationManager, AUTHENTICATION_MANAGER_NOT_EMPTY);
//        Assert.notNull(userService, USER_SERVICE_NOT_EMPTY);
//        Assert.notNull(jwtTokenProvider, JWT_PROVIDER_NOT_EMPTY);
//
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

    public AuthorizationServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
        Assert.notNull(authenticationManager, AUTHENTICATION_MANAGER_NOT_EMPTY);
        Assert.notNull(userService, USER_SERVICE_NOT_EMPTY);

        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @Override
    public Map<Object, Object> login(String email, String password) {
        Assert.notNull(email, LOGIN_PASSWORD_NOT_EMPTY);
        Assert.notNull(password, LOGIN_PASSWORD_NOT_EMPTY);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("user " + email + " not found");
        }
//        String token = jwtTokenProvider.createToken(email, user.getRoles());
        Map<Object, Object> response = new HashMap<>();
        response.put("email", email);
//        response.put("token", token);
        return response;
    }
}
