package com.example.individualprojectbe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TOKENS")
public class LoginToken {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private User user;

    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;
}
