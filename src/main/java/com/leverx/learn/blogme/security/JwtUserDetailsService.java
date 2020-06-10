package com.leverx.learn.blogme.security;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.security.jwt.JwtUser;
import com.leverx.learn.blogme.security.jwt.JwtUserFactory;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Viktar on 09.06.2020
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final UserService userService;


   public JwtUserDetailsService(UserService userService){
       this.userService = userService;
   }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if(user == null){
            throw new    UsernameNotFoundException("User " + email + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
