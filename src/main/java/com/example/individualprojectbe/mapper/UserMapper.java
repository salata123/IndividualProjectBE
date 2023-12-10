package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {
    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getCart()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getCart()
        );
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .toList();
    }

    public List<User> mapToUserList(final List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(this::mapToUser)
                .toList();
    }
}