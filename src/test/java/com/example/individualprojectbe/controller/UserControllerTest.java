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
    void shouldReturnAllUsersWhenRequested() {
        // Given
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When
        ResponseEntity<List<UserDto>> responseEntity = userController.getAllUsers();

        // Then
        assertEquals(userDtos, responseEntity.getBody());
    }

    @Test
    void shouldReturnUserDetailsById() throws UserNotFoundException {
        // Given
        when(userService.getUser(1L)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.getUser(1L);

        // Then
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void shouldReturnUserDetailsByUsername() throws UserNotFoundException {
        // Given
        String username = "testUser";

        when(userService.getUserByUsername(username)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.getUserByUsername(username);

        // Then
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void shouldDeleteUserById() {
        // Given
        long userId = 1L;

        // When
        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        // Then
        verify(userService, times(1)).deleteUser(userId);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void shouldEditUserDetails() {
        // Given
        userDto.setUsername("newUser");

        User user = new User();
        user.setUsername("newUser");

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.editUser(userDto);

        // Then
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void shouldReturnUserOrdersByUsername() throws UserNotFoundException {
        // Given
        String userUsername = "testUser";
        List<Long> orders = Collections.singletonList(1L);

        when(userService.getUserOrders(userUsername)).thenReturn(orders);

        // When
        ResponseEntity<List<Long>> responseEntity = userController.getUserOrders(userUsername);

        // Then
        assertEquals(orders, responseEntity.getBody());
    }

    @Test
    void shouldSuccessfullyLoginWithValidCredentials() throws UserNotFoundException, LoginTokenNotFoundException {
        // Given
        Map<String, String> credentials = Map.of("username", "testUser", "password", "testPassword");

        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(loginTokenService.getLoginToken(1L)).thenReturn(loginToken);
        when(userService.authenticateUser(user.getUsername(), user.getPassword())).thenReturn(true);

        // When
        ResponseEntity<String> responseEntity = userController.login(credentials);

        // Then
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void shouldCreateNewUser() {
        // Given
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userService.isUsernameTaken(user.getUsername())).thenReturn(false);

        // When
        ResponseEntity<UserDto> responseEntity = userController.createUser(userDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());

        assertEquals(userDto.getId(), responseEntity.getBody().getId());
        assertEquals(userDto.getUsername(), responseEntity.getBody().getUsername());
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void shouldCheckIfUsernameIsTaken() {
        // Given
        String username = "testUser";

        when(userService.isUsernameTaken(username)).thenReturn(true);

        // When
        ResponseEntity<Boolean> responseEntity = userController.isUsernameTaken(username);

        // Then
        assertEquals(true, responseEntity.getBody());
    }
}