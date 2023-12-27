package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.LoginTokenDto;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.exception.LoginTokenNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.LoginTokenMapper;
import com.example.individualprojectbe.service.LoginTokenService;
import com.example.individualprojectbe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTokenControllerTest {

    @Mock
    private LoginTokenService loginTokenService;

    @Mock
    private LoginTokenMapper loginTokenMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginTokenController loginTokenController;

    private LoginToken loginToken;
    private LoginTokenDto loginTokenDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize objects for testing
        loginToken = new LoginToken(1L, 101L, LocalDateTime.now().plusMinutes(5));
        loginTokenDto = new LoginTokenDto(1L, 101L, LocalDateTime.now().plusMinutes(5));
        user = new User(201L, "testUser", "password", 301L, new ArrayList<>(), 1L);
    }

    @Test
    void getAllLoginTokensTest() {
        List<LoginToken> loginTokens = List.of(loginToken);
        when(loginTokenService.getAllLoginTokens()).thenReturn(loginTokens);
        when(loginTokenMapper.mapToLoginTokenDtoList(loginTokens)).thenReturn(List.of(loginTokenDto));

        ResponseEntity<List<LoginTokenDto>> responseEntity = loginTokenController.getAllLoginTokens();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(loginTokenDto, responseEntity.getBody().get(0));

        verify(loginTokenService, times(1)).getAllLoginTokens();
        verify(loginTokenMapper, times(1)).mapToLoginTokenDtoList(loginTokens);
    }

    @Test
    void getLoginTokenTest() throws LoginTokenNotFoundException {
        when(loginTokenService.getLoginToken(1L)).thenReturn(loginToken);
        when(loginTokenMapper.mapToLoginTokenDto(loginToken)).thenReturn(loginTokenDto);

        ResponseEntity<LoginTokenDto> responseEntity = loginTokenController.getLoginToken(1L);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(loginTokenDto, responseEntity.getBody());

        verify(loginTokenService, times(1)).getLoginToken(1L);
        verify(loginTokenMapper, times(1)).mapToLoginTokenDto(loginToken);
    }

    @Test
    void getLoginTokenNotFoundTest() throws LoginTokenNotFoundException {
        when(loginTokenService.getLoginToken(1L)).thenThrow(new LoginTokenNotFoundException());

        ResponseEntity<LoginTokenDto> responseEntity = loginTokenController.getLoginToken(1L);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(loginTokenService, times(1)).getLoginToken(1L);
        verify(loginTokenMapper, times(0)).mapToLoginTokenDto(loginToken);
    }

    @Test
    void checkTokenExpirationWithExistingTokenExpiredTest() throws LoginTokenNotFoundException, UserNotFoundException {
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(loginTokenService.getLoginToken(user.getLoginTokenId())).thenReturn(loginToken);
        when(loginTokenService.isTokenExpired(loginToken)).thenReturn(true);

        ResponseEntity<Boolean> responseEntity = loginTokenController.checkTokenExpiration("testUser");

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody());

        verify(userService, times(1)).getUserByUsername("testUser");
        verify(loginTokenService, times(1)).getLoginToken(user.getLoginTokenId());
        verify(loginTokenService, times(1)).isTokenExpired(loginToken);
    }

    @Test
    void checkTokenExpirationWithoutExistingTokenTest() throws UserNotFoundException, LoginTokenNotFoundException {
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        user.setLoginTokenId(null);
        when(loginTokenService.saveLoginToken(any())).thenReturn(new LoginToken(2L, 201L, LocalDateTime.now().plusMinutes(1)));

        ResponseEntity<Boolean> responseEntity = loginTokenController.checkTokenExpiration("testUser");

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());

        verify(userService, times(1)).getUserByUsername("testUser");
        verify(loginTokenService, times(1)).saveLoginToken(any());
    }

    @Test
    void deleteLoginTokenTest() {
        ResponseEntity<Void> responseEntity = loginTokenController.deleteLoginToken(1L);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(loginTokenService, times(1)).deleteLoginToken(1L);
    }

    @Test
    void createLoginTokenTest() {
        LoginTokenDto inputDto = new LoginTokenDto(null, 101L, LocalDateTime.now().plusMinutes(5));
        LoginToken inputToken = new LoginToken(null, 101L, LocalDateTime.now().plusMinutes(5));

        when(loginTokenMapper.mapToLoginToken(inputDto)).thenReturn(inputToken);

        ResponseEntity<LoginTokenDto> responseEntity = loginTokenController.createLoginToken(inputDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(loginTokenMapper, times(1)).mapToLoginToken(inputDto);
        verify(loginTokenService, times(1)).saveLoginToken(inputToken);
    }

    @Test
    void editLoginTokenTest() {
        LoginTokenDto inputDto = new LoginTokenDto(1L, 101L, LocalDateTime.now().plusMinutes(5));
        LoginToken inputToken = new LoginToken(1L, 101L, LocalDateTime.now().plusMinutes(5));

        when(loginTokenMapper.mapToLoginToken(inputDto)).thenReturn(inputToken);
        when(loginTokenService.saveLoginToken(inputToken)).thenReturn(inputToken);
        when(loginTokenMapper.mapToLoginTokenDto(inputToken)).thenReturn(inputDto);

        ResponseEntity<LoginTokenDto> responseEntity = loginTokenController.editLoginToken(inputDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(inputDto, responseEntity.getBody());

        verify(loginTokenMapper, times(1)).mapToLoginToken(inputDto);
        verify(loginTokenService, times(1)).saveLoginToken(inputToken);
        verify(loginTokenMapper, times(1)).mapToLoginTokenDto(inputToken);
    }
}