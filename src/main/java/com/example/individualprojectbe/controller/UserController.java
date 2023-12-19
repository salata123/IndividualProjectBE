package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.LoginToken;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.domain.UserDto;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) throws UserNotFoundException{
        String username = credentials.get("username");
        String password = credentials.get("password");
        boolean isAuthenticated = userService.authenticateUser(username, password);

        User user = userService.getUserByUsername(username);

        if (isAuthenticated) {
            LoginToken loginToken = user.getLoginToken();

            if (loginToken != null) {
                // Update the expiration date of the existing token
                loginToken.setExpirationDate(LocalDateTime.now().plusMinutes(1));
                loginTokenService.saveLoginToken(loginToken);
            } else {
                // Create a new token if none exists
                loginToken = new LoginToken();
                loginToken.setUser(user);
                loginToken.setExpirationDate(LocalDateTime.now().plusMinutes(1));
                loginTokenService.saveLoginToken(loginToken);
            }

            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            // Check if the username is already taken
            if (userService.isUsernameTaken(userDto.getUsername())) {
                return ResponseEntity.status(400).body(null); // Return a 400 Bad Request status
            }

            // Map UserDto to User entity
            User user = userMapper.mapToUser(userDto);

            // Create a new Cart
            Cart cart = new Cart();
            // Set flightList or perform additional operations on the cart if needed

            // Set the user to the cart
            cart.setUser(user);

            // Set the cart to the user
            user.setCart(cart);

            // Save the user (which should cascade to the associated Cart)
            userService.saveUser(user);

            // Return the created user DTO
            return ResponseEntity.ok(userMapper.mapToUserDto(user));
        } catch (Exception e) {
            // Handle exceptions (e.g., database error)
            return ResponseEntity.status(500).body(null); // Return a 500 Internal Server Error status
        }
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        boolean isTaken = userService.isUsernameTaken(username);
        return ResponseEntity.ok(isTaken);
    }
}
