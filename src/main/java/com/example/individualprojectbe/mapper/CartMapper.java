package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart mapToCart(CartDto cartDto);
    CartDto mapToCartDto(Cart cart);
    List<CartDto> mapToCartDtoList(List<Cart> cartList);
    List<Cart> mapToCartList(List<CartDto> cartList);
}
