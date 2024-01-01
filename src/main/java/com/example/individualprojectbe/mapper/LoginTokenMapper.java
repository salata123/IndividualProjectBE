package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.LoginTokenDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoginTokenMapper {
    LoginToken mapToLoginToken(LoginTokenDto loginTokenDto);
    LoginTokenDto mapToLoginTokenDto(LoginToken loginToken);
    List<LoginTokenDto> mapToLoginTokenDtoList(List<LoginToken> loginTokenList);
    List<LoginToken> mapToLoginTokenList(List<LoginTokenDto> loginTokenList);
}
