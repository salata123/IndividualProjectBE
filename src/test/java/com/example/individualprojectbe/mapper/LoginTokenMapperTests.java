package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.LoginTokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginTokenMapperTests {
    private LoginTokenMapper loginTokenMapper;
    private LoginTokenDto loginTokenDto;
    private LoginToken loginToken;

    @BeforeEach
    void setUp() {
        loginTokenMapper = Mappers.getMapper(LoginTokenMapper.class);

        loginToken = new LoginToken(1L, 123L, LocalDateTime.of(2023, 1, 1, 12, 0));
        loginTokenDto = new LoginTokenDto(1L, 123L, LocalDateTime.of(2023, 1, 1, 12, 0));
    }

    @Test
    void mapToLoginToken() {
        LoginToken mappedLoginToken = loginTokenMapper.mapToLoginToken(loginTokenDto);

        assertNotNull(mappedLoginToken);
        assertEquals(loginTokenDto.getId(), mappedLoginToken.getId());
        assertEquals(loginTokenDto.getUserId(), mappedLoginToken.getUserId());
        assertEquals(loginTokenDto.getExpirationDate(), mappedLoginToken.getExpirationDate());
    }

    @Test
    void mapToLoginTokenDto() {
        LoginTokenDto mappedLoginTokenDto = loginTokenMapper.mapToLoginTokenDto(loginToken);

        assertNotNull(mappedLoginTokenDto);
        assertEquals(loginToken.getId(), mappedLoginTokenDto.getId());
        assertEquals(loginToken.getUserId(), mappedLoginTokenDto.getUserId());
        assertEquals(loginToken.getExpirationDate(), mappedLoginTokenDto.getExpirationDate());
    }

    @Test
    void mapToLoginTokenDtoList() {
        List<LoginToken> loginTokenList = List.of(loginToken);
        List<LoginTokenDto> mappedLoginTokenDtoList = loginTokenMapper.mapToLoginTokenDtoList(loginTokenList);

        assertNotNull(mappedLoginTokenDtoList);
        assertEquals(1, mappedLoginTokenDtoList.size());
    }

    @Test
    void mapToLoginTokenList() {
        List<LoginTokenDto> loginTokenDtoList = List.of(loginTokenDto);
        List<LoginToken> mappedLoginTokenList = loginTokenMapper.mapToLoginTokenList(loginTokenDtoList);

        assertNotNull(mappedLoginTokenList);
        assertEquals(1, mappedLoginTokenList.size());
    }
}
