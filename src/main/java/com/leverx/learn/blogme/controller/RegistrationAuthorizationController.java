package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.authenticationDto.AuthenticationRequestDto;
import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.RegistrationService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Viktar on 07.06.2020
 */
@RestController
@RequestMapping("/auth")
public class RegistrationAuthorizationController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final RegistrationService registrationService;
    private final ActivationCodeService codeService;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public RegistrationAuthorizationController(UserService userService, UserDtoConverter userDtoConverter,
                                               RegistrationService registrationService, ActivationCodeService codeService,
                                               MailService mailService, BCryptPasswordEncoder passwordEncoder,
                                               AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        Assert.notNull(userService, "userService must not be null");
        Assert.notNull(userDtoConverter, "userDtoConverter must not be null");
        Assert.notNull(registrationService, "registrationService must not be null");
        Assert.notNull(codeService, "codeService must not be null");
        Assert.notNull(mailService, "mailService must not be null");
        Assert.notNull(passwordEncoder, "passwordEncoder must not be null");

        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.registrationService = registrationService;
        this.codeService = codeService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody User user) {
        Assert.notNull(user, "user must not be null");

        return userDtoConverter.convertToDto(registrationService.registerUser(user));
    }

    @GetMapping("/confirm/{activationCode}")
    public void confirmRegistration(@PathVariable String activationCode) {
        Assert.notNull(activationCode, "activationCode must not be null");

        codeService.activateUserByCode(activationCode);
    }

    @PostMapping("/forgot_password")
    public void forgotPassword(@RequestBody String email) {
        Assert.notNull(email, "email must not be empty");

        String code = codeService.generateActivationCode(email);
        mailService.send(email, "Password resetting", "use this link for reset password: " + code);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody CodeAndPasswordHolder holder) {
        Assert.notNull(holder, "code and password must not be empty");

        String code = holder.code;
        String password = holder.password;
        codeService.resetPassword(code, passwordEncoder.encode(password));
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestBody String activationCode) {
        Assert.notNull(activationCode, "activatioCode must not be null");

        if (codeService.isCodeExists(activationCode)) {
            return new ResponseEntity<>("Activation code is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Activation code is not valid", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        Assert.notNull(requestDto, "requestDto must not be null");

        String username = requestDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = userService.getUserByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("user " + username + " not found");
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("token", jwtTokenProvider.createToken(username));
        return ResponseEntity.ok(response);
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
