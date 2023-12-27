package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.controller.UserController;
import com.example.individualprojectbe.domain.*;
import com.example.individualprojectbe.exception.LoginTokenNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.UserMapper;
import com.example.individualprojectbe.service.CartService;
import com.example.individualprojectbe.service.LoginTokenService;
import com.example.individualprojectbe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private LoginTokenService loginTokenService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        // Arrange
        List<User> users = Collections.singletonList(new User());
        List<UserDto> userDtos = Collections.singletonList(new UserDto());

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // Act
        ResponseEntity<List<UserDto>> responseEntity = userController.getAllUsers();

        // Assert
        assertEquals(userDtos, responseEntity.getBody());
    }

    @Test
    void getUser() throws UserNotFoundException {
        // Arrange
        long userId = 1L;
        User user = new User();
        UserDto userDto = new UserDto();

        when(userService.getUser(userId)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.getUser(userId);

        // Assert
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void getUserByUsername() throws UserNotFoundException {
        // Arrange
        String username = "testUser";
        User user = new User();
        UserDto userDto = new UserDto();

        when(userService.getUserByUsername(username)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.getUserByUsername(username);

        // Assert
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void deleteUser() {
        // Arrange
        long userId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        // Assert
        verify(userService, times(1)).deleteUser(userId);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void editUser() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        User savedUser = new User();

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(savedUser);
        when(userMapper.mapToUserDto(savedUser)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.editUser(userDto);

        // Assert
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void getUserOrders() throws UserNotFoundException {
        // Arrange
        String userUsername = "testUser";
        List<Long> orders = Collections.singletonList(1L);

        when(userService.getUserOrders(userUsername)).thenReturn(orders);

        // Act
        ResponseEntity<List<Long>> responseEntity = userController.getUserOrders(userUsername);

        // Assert
        assertEquals(orders, responseEntity.getBody());
    }

    @Test
    void login() throws UserNotFoundException, LoginTokenNotFoundException {
        // Arrange
        Map<String, String> credentials = Map.of("username", "testUser", "password", "testPassword");

        // Mock User and LoginToken creation
        User user = new User();
        user.setLoginTokenId(1L);
        when(userService.getUserByUsername("testUser")).thenReturn(user);

        LoginToken loginToken = new LoginToken();
        when(loginTokenService.getLoginToken(1L)).thenReturn(loginToken);

        // Act
        ResponseEntity<String> responseEntity = userController.login(credentials);

        // Assert
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void createUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("newUser");

        User user = new User();
        user.setUsername("newUser");

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.isUsernameTaken("newUser")).thenReturn(false);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.createUser(userDto);

        // Assert
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void isUsernameTaken() {
        // Arrange
        String username = "testUser";

        when(userService.isUsernameTaken(username)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> responseEntity = userController.isUsernameTaken(username);

        // Assert
        assertEquals(true, responseEntity.getBody());
    }
}
