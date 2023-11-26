package com.example.individualprojectbe.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "CARTS")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();
}
