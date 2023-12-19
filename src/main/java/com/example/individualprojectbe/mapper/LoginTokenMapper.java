package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.LoginTokenDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginTokenMapper {
    public LoginToken mapToLoginToken(final LoginTokenDto loginTokenDto) {
        return new LoginToken(
                loginTokenDto.getId(),
                loginTokenDto.getUser(),
                loginTokenDto.getExpirationDate()
        );
    }

    public LoginTokenDto mapToLoginTokenDto(final LoginToken loginToken) {
        return new LoginTokenDto(
                loginToken.getId(),
                loginToken.getUser(),
                loginToken.getExpirationDate()
        );
    }

    public List<LoginTokenDto> mapToLoginTokenDtoList(final List<LoginToken> loginTokenList) {
        return loginTokenList.stream()
                .map(this::mapToLoginTokenDto)
                .toList();
    }

    public List<LoginToken> mapToLoginTokenList(final List<LoginTokenDto> loginTokenList) {
        return loginTokenList.stream()
                .map(this::mapToLoginToken)
                .toList();
    }
}
