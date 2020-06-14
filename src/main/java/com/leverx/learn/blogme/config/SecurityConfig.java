package com.leverx.learn.blogme.config;

import com.leverx.learn.blogme.security.JwtUserDetailsServiceImpl;
import com.leverx.learn.blogme.security.filter.JwtTokenFilter;
import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.util.Assert;

/**
 * @author Viktar on 09.06.2020
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;
    private final JwtUserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtTokenProvider tokenProvider, JwtUserDetailsServiceImpl userDetailsService) {
        Assert.notNull(tokenProvider, "tokenProvider must not be null");
        Assert.notNull(userDetailsService, "userDetailsService must not be null");

        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Assert.notNull(http, "httpSecurity must not be null");

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/articles").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtTokenFilter(authenticationManagerBean(), tokenProvider));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        Assert.notNull(auth, "auth must not be null");

        auth.userDetailsService(userDetailsService);
    }
}
