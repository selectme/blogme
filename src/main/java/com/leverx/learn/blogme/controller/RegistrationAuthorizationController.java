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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.registrationService = registrationService;
        this.codeService = codeService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody UserDto userDto) {

        User user = userDtoConverter.convertToEntity(userDto);
        if (userService.getUserByEmail(user.getEmail()) == null) {
            codeService.generateActivationCode(user.getEmail());
            String activationCode = codeService.generateActivationCode(user.getEmail());
            mailService.send(user.getEmail(), "activation link", "http://localhost:8080/auth/confirm/" + activationCode);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(new Date());
            userService.addUser(user);
        } else {
            throw new IllegalStateException("Email " + user.getEmail() + " has already registered");
        }
        return userDtoConverter.convertToDto(user);
    }

    @GetMapping("/confirm/{activationCode}")
    public void confirmRegistration(@PathVariable String activationCode) {
        registrationService.activateUserByConfirmationCode(activationCode);
    }

    @PostMapping("/forgot_password")
    public void forgottenPasswordSendNewCode(@RequestBody String email) {
        String code = codeService.generateActivationCode(email);
        mailService.send(email, "forget password", "Use this code for resetting password: " + code);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody CodeAndPasswordHolder holder) {
        String code = holder.code;
        String password = holder.password;
        codeService.resetPassword(code, passwordEncoder.encode(password));
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestBody String code) {
        if (codeService.isCodeExists(code)) {
            return new ResponseEntity<>("Code " + code + " is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Code " + code + "is not valid", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
            String username = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.getUserByEmail(username);
            if(user == null){
                throw new UsernameNotFoundException("user " + username + " not found");
            }
            String token  = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
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
