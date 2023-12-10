package com.example.individualprojectbe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Cart cart;
}