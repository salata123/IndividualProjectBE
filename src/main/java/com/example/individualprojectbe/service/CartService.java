package com.example.individualprojectbe.service;

import com.example.individualprojectbe.exception.CartNotFoundException;
import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    public List<Cart> getAllCarts() {
        return repository.findAll();
    }

    public Cart saveCart(final Cart cart) {
        return repository.save(cart);
    }

    public Cart getCart(final Long id) throws CartNotFoundException {
        return repository.findById(id).orElseThrow(CartNotFoundException::new);
    }

    public void deleteCart(final Long id) {
        repository.deleteById(id);
    }
}
