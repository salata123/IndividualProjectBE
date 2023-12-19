package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.LoginTokenDto;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.exception.LoginTokenNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.LoginTokenMapper;
import com.example.individualprojectbe.service.LoginTokenService;
import com.example.individualprojectbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/loginTokens")
@RequiredArgsConstructor
public class LoginTokenController {
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private LoginTokenMapper loginTokenMapper;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<LoginTokenDto>> getAllLoginTokens(){
        List<LoginToken> loginTokens = loginTokenService.getAllLoginTokens();
        return ResponseEntity.ok(loginTokenMapper.mapToLoginTokenDtoList(loginTokens));
    }

    @GetMapping("{loginTokenId}")
    public ResponseEntity<LoginTokenDto> getLoginToken(@PathVariable long loginTokenId) throws LoginTokenNotFoundException {
        return ResponseEntity.ok(loginTokenMapper.mapToLoginTokenDto(loginTokenService.getLoginToken(loginTokenId)));
    }

    @GetMapping("/checkExpiration/{username}")
    public ResponseEntity<Boolean> checkTokenExpiration(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            LoginToken loginToken = user.getLoginToken();

            if (loginToken != null) {
                boolean isExpired = loginTokenService.isTokenExpired(loginToken);
                System.out.println("Time left:" + (Duration.between(LocalDateTime.now(), loginToken.getExpirationDate())));
                return ResponseEntity.ok(isExpired);
            } else {
                // Handle the case where the user or login token is null
                return ResponseEntity.notFound().build();
            }
        } catch (UserNotFoundException e) {
            // Handle the case where the user is not found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{loginTokenId}")
    public ResponseEntity<Void> deleteLoginToken(@PathVariable long loginTokenId){
        loginTokenService.deleteLoginToken(loginTokenId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginTokenDto> createLoginToken(@RequestBody LoginTokenDto loginTokenDto){
        LoginToken loginToken = loginTokenMapper.mapToLoginToken(loginTokenDto);
        loginTokenService.saveLoginToken(loginToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{loginTokenId}")
    public ResponseEntity<LoginTokenDto> editLoginToken(@RequestBody LoginTokenDto loginTokenDto){
        LoginToken loginToken = loginTokenMapper.mapToLoginToken(loginTokenDto);
        LoginToken savedLoginToken = loginTokenService.saveLoginToken(loginToken);
        return ResponseEntity.ok(loginTokenMapper.mapToLoginTokenDto(savedLoginToken));
    }
}
