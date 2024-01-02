package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import com.example.individualprojectbe.exception.CartNotFoundException;
import com.example.individualprojectbe.mapper.CartMapper;
import com.example.individualprojectbe.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartMapper cartMapper;
    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts(){
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(cartMapper.mapToCartDtoList(carts));
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable long cartId){
        try {
            return ResponseEntity.ok(cartMapper.mapToCartDto(cartService.getCart(cartId)));
        } catch (CartNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable long cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto){
        Cart cart = cartMapper.mapToCart(cartDto);
        cartService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{cartId}")
    public ResponseEntity<CartDto> editCart(@RequestBody CartDto cartDto){
        Cart cart = cartMapper.mapToCart(cartDto);
        Cart savedCart = cartService.saveCart(cart);
        return ResponseEntity.ok(cartMapper.mapToCartDto(savedCart));
    }

    @PostMapping("{cartId}/addFlight/{flightId}")
    public ResponseEntity<CartDto> addFlightToCart(@PathVariable long cartId, @PathVariable long flightId) {
        try {
            Cart updatedCart = cartService.addFlightToCart(cartId, flightId);
            return ResponseEntity.ok(cartMapper.mapToCartDto(updatedCart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{cartId}/removeFlight/{flightId}")
    public ResponseEntity<CartDto> removeFlightFromCart(@PathVariable long cartId, @PathVariable long flightId) {
        try {
            Cart updatedCart = cartService.removeFlightFromCart(cartId, flightId);
            return ResponseEntity.ok(cartMapper.mapToCartDto(updatedCart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
