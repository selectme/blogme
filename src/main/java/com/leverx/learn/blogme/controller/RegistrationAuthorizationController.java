package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.dto.userdto.UserDto;
import com.leverx.learn.blogme.dto.userdto.UserDtoConverter;
import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.MailService;
import com.leverx.learn.blogme.service.RegistrationService;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    public RegistrationAuthorizationController(UserService userService, UserDtoConverter userDtoConverter,
                                               RegistrationService registrationService, ActivationCodeService codeService,
                                               MailService mailService) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.registrationService = registrationService;
        this.codeService = codeService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody UserDto userDto) {

        User user = userDtoConverter.convertToEntity(userDto);
        if (userService.getUserByEmail(user.getEmail()) == null) {
            codeService.generateActivationCode(user.getEmail());
            String activationCode = codeService.generateActivationCode(user.getEmail());
            mailService.send(user.getEmail(), "activation link", "http://localhost:8080/auth/confirm/" + activationCode);
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
        codeService.resetPassword(code, password);
    }

    @GetMapping("/check_code")
    public ResponseEntity<String> checkCode(@RequestBody String code) {
        if (codeService.isCodeExists(code)) {
            return new ResponseEntity<>("Code " + code + " is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Code " + code + "is not valid", HttpStatus.BAD_REQUEST);
        }
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
