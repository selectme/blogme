package com.leverx.learn.blogme.security;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.security.jwt.JwtUser;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Viktar on 09.06.2020
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

   public JwtUserDetailsServiceImpl(UserService userService){
       this.userService = userService;
   }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Assert.hasText(email, "email must not be empty");

        User user = userService.getUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return new JwtUser(user.getEmail(), user.getPassword(), user.isEnabled());
    }
}
