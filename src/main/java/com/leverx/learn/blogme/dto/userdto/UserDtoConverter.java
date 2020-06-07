package com.leverx.learn.blogme.dto.userdto;

import com.leverx.learn.blogme.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Viktar on 31.05.2020
 */
@Component
public class UserDtoConverter {

    public UserDto convertToDto(User user) {

        UserDto userDto = new UserDto();

//        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setArticles(user.getArticles());
        userDto.setEnabled(user.isEnabled());
        return userDto;

    }


    public User convertToEntity(UserDto userDto) {

        User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setEnabled(userDto.isEnabled());

        return user;
    }
}
