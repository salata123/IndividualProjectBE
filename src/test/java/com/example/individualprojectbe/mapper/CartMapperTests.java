package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CartMapperTests {
    private final CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Test
    public void testMapToCart() {
        // Given
        CartDto cartDto = new CartDto(1L, 1L, Arrays.asList(100L, 101L));

        // When
        Cart cart = cartMapper.mapToCart(cartDto);

        // Then
        assertNotNull(cart);
        assertEquals(cartDto.getId(), cart.getId());
        assertEquals(cartDto.getUserId(), cart.getUserId());
        assertEquals(cartDto.getFlightList(), cart.getFlightList());
    }

    @Test
    public void testMapToCartDto() {
        // Given
        Cart cart = new Cart(1L, 200L, Arrays.asList(102L, 103L));

        // When
        CartDto cartDto = cartMapper.mapToCartDto(cart);

        // Then
        assertNotNull(cartDto);
        assertEquals(cart.getId(), cartDto.getId());
        assertEquals(cart.getUserId(), cartDto.getUserId());
        assertEquals(cart.getFlightList(), cartDto.getFlightList());
    }

    @Test
    public void testMapToCartDtoList() {
        // Given
        Cart cart1 = new Cart(1L, 300L, Arrays.asList(104L, 105L));
        Cart cart2 = new Cart(2L, 400L, Arrays.asList(106L, 107L));

        // When
        List<CartDto> cartDtoList = cartMapper.mapToCartDtoList(Arrays.asList(cart1, cart2));

        // Then
        assertNotNull(cartDtoList);
        assertEquals(2, cartDtoList.size());

        CartDto mappedCartDto1 = cartDtoList.get(0);
        assertEquals(cart1.getId(), mappedCartDto1.getId());
        assertEquals(cart1.getUserId(), mappedCartDto1.getUserId());
        assertEquals(cart1.getFlightList(), mappedCartDto1.getFlightList());

        CartDto mappedCartDto2 = cartDtoList.get(1);
        assertEquals(cart2.getId(), mappedCartDto2.getId());
        assertEquals(cart2.getUserId(), mappedCartDto2.getUserId());
        assertEquals(cart2.getFlightList(), mappedCartDto2.getFlightList());
    }

    @Test
    public void testMapToCartList() {
        // Given
        CartDto cartDto1 = new CartDto(1L, 1L, Arrays.asList(108L, 109L));
        CartDto cartDto2 = new CartDto(2L, 1L, Arrays.asList(110L, 111L));

        // When
        List<Cart> cartList = cartMapper.mapToCartList(Arrays.asList(cartDto1, cartDto2));

        // Then
        assertNotNull(cartList);
        assertEquals(2, cartList.size());

        Cart mappedCart1 = cartList.get(0);
        assertEquals(cartDto1.getId(), mappedCart1.getId());
        assertEquals(cartDto1.getUserId(), mappedCart1.getUserId());
        assertEquals(cartDto1.getFlightList(), mappedCart1.getFlightList());

        Cart mappedCart2 = cartList.get(1);
        assertEquals(cartDto2.getId(), mappedCart2.getId());
        assertEquals(cartDto2.getUserId(), mappedCart2.getUserId());
        assertEquals(cartDto2.getFlightList(), mappedCart2.getFlightList());
    }
}
