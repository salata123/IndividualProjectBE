package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserDto userDto);
    UserDto mapToUserDto(User user);
    List<UserDto> mapToUserDtoList(List<User> userList);
    List<User> mapToUserList(List<UserDto> userDtoList);
}
