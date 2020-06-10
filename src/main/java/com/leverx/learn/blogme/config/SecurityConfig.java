package com.leverx.learn.blogme.config;

import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Viktar on 09.06.2020
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider tokenProvider;

    public SecurityConfig(JwtTokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("").permitAll()
                    .antMatchers("").hasRole("ADMIN")
                    .anyRequest().authenticated();

    }
}
