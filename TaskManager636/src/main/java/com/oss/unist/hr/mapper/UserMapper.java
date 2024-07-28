package com.oss.unist.hr.mapper;

import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.User;
import com.oss.unist.hr.util.PasswordUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public class UserMapper {

    public static User mapDtoToEntity(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setCountry(userDto.getCountry());
        user.setRole(userDto.getRole());
        user.setPassword(PasswordUtils.hashPassword(userDto.getPassword()));
        return user;
    }

    public UserDTO convertToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setCountry(user.getCountry());
        userDto.setRole(user.getRole());
        return userDto;
    }

}