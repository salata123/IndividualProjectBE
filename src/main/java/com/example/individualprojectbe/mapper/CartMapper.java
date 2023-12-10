package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartMapper {
    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getId(),
                cartDto.getUser(),
                cartDto.getFlightList()
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser(),
                cart.getFlightList()
        );
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> cartList) {
        return cartList.stream()
                .map(this::mapToCartDto)
                .toList();
    }

    public List<Cart> mapToCartList(final List<CartDto> cartList) {
        return cartList.stream()
                .map(this::mapToCart)
                .toList();
    }
}
