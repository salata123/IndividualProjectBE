package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import com.example.individualprojectbe.exception.CartNotFoundException;
import com.example.individualprojectbe.mapper.CartMapper;
import com.example.individualprojectbe.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartController cartController;

    private Cart cart;
    private CartDto cartDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cart = new Cart(1L, 101L, List.of(201L, 202L));
        cartDto = new CartDto(1L, 101L, List.of(201L, 202L));
    }

    @Test
    void getAllCartsTest() {
        // Given
        when(cartService.getAllCarts()).thenReturn(List.of(cart));
        when(cartMapper.mapToCartDtoList(List.of(cart))).thenReturn(List.of(cartDto));

        // When
        ResponseEntity<List<CartDto>> responseEntity = cartController.getAllCarts();

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(cartDto, responseEntity.getBody().get(0));

        verify(cartService, times(1)).getAllCarts();
        verify(cartMapper, times(1)).mapToCartDtoList(List.of(cart));
    }

    @Test
    void getCartTest() throws CartNotFoundException {
        // Given
        when(cartService.getCart(1L)).thenReturn(cart);
        when(cartMapper.mapToCartDto(cart)).thenReturn(cartDto);

        // When
        ResponseEntity<CartDto> responseEntity = cartController.getCart(1L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(cartDto, responseEntity.getBody());

        verify(cartService, times(1)).getCart(1L);
        verify(cartMapper, times(1)).mapToCartDto(cart);
    }

    @Test
    void getCartNotFoundTest() throws CartNotFoundException {
        // Given
        when(cartService.getCart(1L)).thenThrow(new CartNotFoundException());

        // When
        ResponseEntity<CartDto> responseEntity = cartController.getCart(1L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(cartService, times(1)).getCart(1L);
    }

    @Test
    void deleteCartTest() {
        // When
        ResponseEntity<Void> responseEntity = cartController.deleteCart(1L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(cartService, times(1)).deleteCart(1L);
    }

    @Test
    void createCartTest() {
        // Given
        when(cartMapper.mapToCart(cartDto)).thenReturn(cart);

        // When
        ResponseEntity<CartDto> responseEntity = cartController.createCart(cartDto);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(cartService, times(1)).saveCart(cart);
    }

    @Test
    void editCartTest() {
        // Given
        when(cartMapper.mapToCart(cartDto)).thenReturn(cart);
        when(cartService.saveCart(cart)).thenReturn(cart);

        // When
        ResponseEntity<CartDto> responseEntity = cartController.editCart(cartDto);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        if (responseEntity.getBody() != null) {
            assertEquals(cartDto, responseEntity.getBody());
        }

        verify(cartService, times(1)).saveCart(cart);
        verify(cartMapper, times(1)).mapToCartDto(cart);
    }

    @Test
    void addFlightToCartTest() throws CartNotFoundException {
        // Given
        when(cartService.addFlightToCart(1L, 2L)).thenReturn(cart);
        when(cartMapper.mapToCartDto(cart)).thenReturn(cartDto);

        // When
        ResponseEntity<CartDto> responseEntity = cartController.addFlightToCart(1L, 2L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(cartDto, responseEntity.getBody());

        verify(cartService, times(1)).addFlightToCart(1L, 2L);
        verify(cartMapper, times(1)).mapToCartDto(cart);
    }

    @Test
    void addFlightToCartNotFoundTest() throws CartNotFoundException {
        // Given
        when(cartService.addFlightToCart(1L, 2L)).thenThrow(new CartNotFoundException());

        // When
        ResponseEntity<CartDto> responseEntity = cartController.addFlightToCart(1L, 2L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(cartService, times(1)).addFlightToCart(1L, 2L);
        verify(cartMapper, times(0)).mapToCartDto(cart);
    }

    @Test
    void removeFlightFromCartTest() throws CartNotFoundException {
        // Given
        when(cartService.removeFlightFromCart(1L, 2L)).thenReturn(cart);
        when(cartMapper.mapToCartDto(cart)).thenReturn(cartDto);

        // When
        ResponseEntity<CartDto> responseEntity = cartController.removeFlightFromCart(1L, 2L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(cartDto, responseEntity.getBody());

        verify(cartService, times(1)).removeFlightFromCart(1L, 2L);
        verify(cartMapper, times(1)).mapToCartDto(cart);
    }

    @Test
    void removeFlightFromCartNotFoundTest() throws CartNotFoundException {
        // Given
        when(cartService.removeFlightFromCart(1L, 2L)).thenThrow(new CartNotFoundException());

        // When
        ResponseEntity<CartDto> responseEntity = cartController.removeFlightFromCart(1L, 2L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(cartService, times(1)).removeFlightFromCart(1L, 2L);
        verify(cartMapper, times(0)).mapToCartDto(cart);
    }
}
