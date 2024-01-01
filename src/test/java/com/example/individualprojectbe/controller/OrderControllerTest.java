package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import com.example.individualprojectbe.domain.User;
import com.example.individualprojectbe.exception.OrderNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.OrderMapper;
import com.example.individualprojectbe.service.OrderService;
import com.example.individualprojectbe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sampleOrder = new Order(1L, 1L, 1L, List.of(1L, 2L));
        sampleUser = new User(1L, "sampleUser", "password", 1L, List.of(1L), 1L);
    }

    @Test
    void getAllOrders() {
        // Given
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(sampleOrder));
        when(orderMapper.mapToOrderDtoList(anyList())).thenReturn(Collections.singletonList(new OrderDto(1L, 1L, 1L, List.of(1L, 2L))));

        // When
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrders();

        // Then
        assertEquals(200, response.getStatusCodeValue());

        List<OrderDto> orderDtos = response.getBody();
        assertNotNull(orderDtos);
        assertEquals(1, orderDtos.size());

        OrderDto resultOrderDto = orderDtos.get(0);
        assertNotNull(resultOrderDto);

        assertEquals(sampleOrder.getId(), resultOrderDto.getId());
        assertEquals(sampleOrder.getCartId(), resultOrderDto.getCartId());
        assertEquals(sampleOrder.getUserId(), resultOrderDto.getUserId());
        assertEquals(sampleOrder.getFlights(), resultOrderDto.getFlights());
    }

    @Test
    void getOrder() throws OrderNotFoundException {
        // Given
        long orderId = 1L;
        when(orderService.getOrder(orderId)).thenReturn(sampleOrder);
        when(orderMapper.mapToOrderDto(any())).thenReturn(new OrderDto(1L, 1L, 1L, List.of(1L, 2L)));

        // When
        ResponseEntity<OrderDto> response = orderController.getOrder(orderId);

        // Then
        assertEquals(200, response.getStatusCodeValue());

        OrderDto resultOrderDto = response.getBody();
        assertNotNull(resultOrderDto);

        assertEquals(sampleOrder.getId(), resultOrderDto.getId());
        assertEquals(sampleOrder.getCartId(), resultOrderDto.getCartId());
        assertEquals(sampleOrder.getUserId(), resultOrderDto.getUserId());
        assertEquals(sampleOrder.getFlights(), resultOrderDto.getFlights());
    }

    @Test
    void deleteOrder() {
        // Given
        long orderId = 1L;

        // When
        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).deleteOrder(orderId);
    }

    @Test
    void createOrder() throws UserNotFoundException {
        // Given
        OrderDto orderDto = new OrderDto();
        when(orderMapper.mapToOrder(orderDto)).thenReturn(sampleOrder);
        when(orderService.saveOrder(sampleOrder)).thenReturn(sampleOrder);
        when(userService.getUser(anyLong())).thenReturn(sampleUser);

        // When
        ResponseEntity<OrderDto> response = orderController.createOrder(orderDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void editOrder() {
        // Given
        OrderDto orderDto = new OrderDto();
        when(orderMapper.mapToOrder(orderDto)).thenReturn(sampleOrder);
        when(orderService.saveOrder(sampleOrder)).thenReturn(sampleOrder);
        when(orderMapper.mapToOrderDto(sampleOrder)).thenReturn(new OrderDto());

        // When
        ResponseEntity<OrderDto> response = orderController.editOrder(orderDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
    }
}