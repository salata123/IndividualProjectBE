package com.example.individualprojectbe.service;

import com.example.individualprojectbe.controller.UserNotFoundException;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteUser(final Long id) {
        repository.deleteById(id);
    }
}
