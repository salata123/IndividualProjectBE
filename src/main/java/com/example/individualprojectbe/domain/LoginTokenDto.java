package com.example.individualprojectbe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTokenDto {
    private Long id;
    private Long userId;
    private LocalDateTime expirationDate;
}
