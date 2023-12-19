package com.example.individualprojectbe.service;

import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User saveUser(final User user) {
        return repository.save(user);
    }

    public User getUser(final Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByUsername(final String username) throws UserNotFoundException {
        return repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(final Long id) {
        repository.deleteById(id);
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> user = repository.findByUsernameAndPassword(username, password);
        return user.isPresent();
    }

    public boolean isUsernameTaken(String username) {
        return repository.existsByUsername(username);
    }
}
