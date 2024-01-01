package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.domain.UserDto;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
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

    private List<User> users;
    private List<UserDto> userDtos;
    private User user;
    private UserDto userDto;
    private LoginToken loginToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        users = Collections.singletonList(
                new User(1L, "testUser", "testPassword", 1L, Collections.singletonList(1L), 1L)
        );

        userDtos = Collections.singletonList(
                new UserDto(1L, "testUser", "testPassword", 1L, Collections.singletonList(1L), 1L)
        );

        user = new User(1L, "testUser", "testPassword", 1L, Collections.singletonList(1L), 1L);
        userDto = new UserDto(1L, "testUser", "testPassword", 1L, Collections.singletonList(1L), 1L);

        loginToken = new LoginToken();
    }

    @Test
    void getAllUsers() {
        // Arrange
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

        when(userService.getUser(1L)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.getUser(1L);

        // Assert
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void getUserByUsername() throws UserNotFoundException {
        // Arrange
        String username = "testUser";

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
        userDto.setUsername("newUser");

        User user = new User();
        user.setUsername("newUser");

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

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

        // Ensure that the user exists and has the correct password
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(loginTokenService.getLoginToken(1L)).thenReturn(loginToken);
        when(userService.authenticateUser(user.getUsername(), user.getPassword())).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = userController.login(credentials);

        // Assert
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void createUser() {
        // Arrange
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userService.isUsernameTaken(user.getUsername())).thenReturn(false);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.createUser(userDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check for null in the body
        assertNotNull(responseEntity.getBody());

        // If you have specific fields to check, you can add more assertions here
        assertEquals(userDto.getId(), responseEntity.getBody().getId());
        assertEquals(userDto.getUsername(), responseEntity.getBody().getUsername());
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
