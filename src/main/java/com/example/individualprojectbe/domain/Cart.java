package com.example.individualprojectbe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CARTS")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @ElementCollection
    @CollectionTable(
            name = "CART_FLIGHTS",
            joinColumns = @JoinColumn(name = "CART_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"CART_ID", "FLIGHT_ID"})
    )
    @Column(name = "FLIGHT_ID")
    private List<Long> flightList;
}
