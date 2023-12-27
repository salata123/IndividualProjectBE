package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.domain.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserMapperTests {
    private UserMapper userMapper;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        // Initialize objects for testing
        user = new User(1L, "john_doe", "password123", 101L, List.of(201L, 202L), 301L);
        userDto = new UserDto(1L, "john_doe", "password123", 101L, List.of(201L, 202L), 301L);
    }

    @Test
    void mapToUser() {
        User mappedUser = userMapper.mapToUser(userDto);

        assertNotNull(mappedUser);
        assertEquals(userDto.getId(), mappedUser.getId());
        assertEquals(userDto.getUsername(), mappedUser.getUsername());
        assertEquals(userDto.getPassword(), mappedUser.getPassword());
        assertEquals(userDto.getCartId(), mappedUser.getCartId());
        assertEquals(userDto.getOrders(), mappedUser.getOrders());
        assertEquals(userDto.getLoginTokenId(), mappedUser.getLoginTokenId());
    }

    @Test
    void mapToUserDto() {
        UserDto mappedUserDto = userMapper.mapToUserDto(user);

        assertNotNull(mappedUserDto);
        assertEquals(user.getId(), mappedUserDto.getId());
        assertEquals(user.getUsername(), mappedUserDto.getUsername());
        assertEquals(user.getPassword(), mappedUserDto.getPassword());
        assertEquals(user.getCartId(), mappedUserDto.getCartId());
        assertEquals(user.getOrders(), mappedUserDto.getOrders());
        assertEquals(user.getLoginTokenId(), mappedUserDto.getLoginTokenId());
    }

    @Test
    void mapToUserDtoList() {
        List<User> userList = List.of(user);
        List<UserDto> mappedUserDtoList = userMapper.mapToUserDtoList(userList);

        assertNotNull(mappedUserDtoList);
        assertEquals(1, mappedUserDtoList.size());
    }

    @Test
    void mapToUserList() {
        List<UserDto> userDtoList = List.of(userDto);
        List<User> mappedUserList = userMapper.mapToUserList(userDtoList);

        assertNotNull(mappedUserList);
        assertEquals(1, mappedUserList.size());
    }
}
