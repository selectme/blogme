package com.leverx.learn.blogme.dto.authenticationDto;

import org.springframework.stereotype.Component;

/**
 * @author Viktar on 09.06.2020
 */
@Component
public class AuthenticationRequestDto {

    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
