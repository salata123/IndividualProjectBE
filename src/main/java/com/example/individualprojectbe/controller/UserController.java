package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.*;
import com.example.individualprojectbe.exception.LoginTokenNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.UserMapper;
import com.example.individualprojectbe.service.CartService;
import com.example.individualprojectbe.service.LoginTokenService;
import com.example.individualprojectbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTokenService loginTokenService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(users));
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.getUser(userId)));
    }

    @GetMapping("/username/{userUsername}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String userUsername) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.getUserByUsername(userUsername)));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }

    @GetMapping("/{userUsername}/orders")
    public ResponseEntity<List<Long>> getUserOrders(@PathVariable String userUsername) throws UserNotFoundException {
        List<Long> orders = userService.getUserOrders(userUsername);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) throws UserNotFoundException, LoginTokenNotFoundException {
        String username = credentials.get("username");
        String password = credentials.get("password");
        boolean isAuthenticated = userService.authenticateUser(username, password);

        User user = userService.getUserByUsername(username);

        if (isAuthenticated) {
            if (user.getLoginTokenId() != null) {
                Long loginTokenId = user.getLoginTokenId();
                LoginToken loginToken = loginTokenService.getLoginToken(loginTokenId);
                loginToken.setExpirationDate(LocalDateTime.now().plusSeconds(10));
                loginTokenService.saveLoginToken(loginToken);
            } else {
                LoginToken loginToken = new LoginToken();
                loginToken.setUserId(user.getId());
                loginToken.setExpirationDate(LocalDateTime.now().plusSeconds(10));
                System.out.println(loginToken.getUserId());
                System.out.println(loginToken.getId());
                user.setLoginTokenId(null);
                user.setLoginTokenId(loginToken.getId());
                userService.saveUser(user);
                loginTokenService.saveLoginToken(loginToken);
            }
            if(user.getCartId() != null){
            } else {
                Cart cart = new Cart();
                cartService.saveCart(cart);
                cart.setUserId(user.getId());
                cartService.saveCart(cart);
                user.setCartId(cart.getId());
                userService.saveUser(user);
            }

            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            if (userService.isUsernameTaken(userDto.getUsername())) {
                return ResponseEntity.status(400).body(null);
            }
            User user = userMapper.mapToUser(userDto);
            LoginToken loginToken = new LoginToken();
            Cart cart = new Cart();
            cartService.saveCart(cart);
            loginTokenService.saveLoginToken(loginToken);

            cart.setUserId(user.getId());
            user.setCartId(cart.getId());
            user.setLoginTokenId(loginToken.getId());
            loginToken.setUserId(user.getId());
            userService.saveUser(user);
            UserDto mappedUserDto = userMapper.mapToUserDto(user);
            return ResponseEntity.ok(mappedUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        boolean isTaken = userService.isUsernameTaken(username);
        return ResponseEntity.ok(isTaken);
    }
}
