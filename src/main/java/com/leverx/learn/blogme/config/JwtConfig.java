package com.leverx.learn.blogme.config;

import com.leverx.learn.blogme.security.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author Viktar on 09.06.2020
 */
@Component
public class JwtConfig extends SecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;

    public JwtConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }




//    public void configure(HttpSecurity security) throws Exception {
//        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(tokenProvider);
//        security.addFilterBefore(jwtTokenFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationToken.class);
//    }
}
