package com.example.individualprojectbe.service;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.exception.LoginTokenNotFoundException;
import com.example.individualprojectbe.repository.LoginTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginTokenService {
    @Autowired
    private LoginTokenRepository repository;

    public List<LoginToken> getAllLoginTokens() {
        return repository.findAll();
    }

    public LoginToken saveLoginToken(final LoginToken LoginToken) {
        return repository.save(LoginToken);
    }

    public LoginToken getLoginToken(final Long id) throws LoginTokenNotFoundException {
        return repository.findById(id).orElseThrow(LoginTokenNotFoundException::new);
    }

    public static boolean isTokenExpired(LoginToken token) {
        LocalDateTime now = LocalDateTime.now();
        return token.getExpirationDate().isBefore(now);
    }

    public void deleteLoginToken(final Long id) {
        repository.deleteById(id);
    }
}
