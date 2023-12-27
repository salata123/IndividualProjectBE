package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders() {
        // Arrange
        List<Order> orders = Collections.singletonList(new Order());
        List<OrderDto> orderDtos = Collections.singletonList(new OrderDto());

        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderMapper.mapToOrderDtoList(orders)).thenReturn(orderDtos);

        // Act
        ResponseEntity<List<OrderDto>> responseEntity = orderController.getAllOrders();

        // Assert
        assertEquals(orderDtos, responseEntity.getBody());
    }

    @Test
    void getOrder() throws OrderNotFoundException {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(orderService.getOrder(orderId)).thenReturn(order);
        when(orderMapper.mapToOrderDto(order)).thenReturn(orderDto);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.getOrder(orderId);

        // Assert
        assertEquals(orderDto, responseEntity.getBody());
    }

    @Test
    void getOrder_OrderNotFoundException() throws OrderNotFoundException {
        // Arrange
        long orderId = 1L;

        when(orderService.getOrder(orderId)).thenThrow(new OrderNotFoundException());

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> orderController.getOrder(orderId));
    }

    @Test
    void deleteOrder() {
        // Arrange
        long orderId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = orderController.deleteOrder(orderId);

        // Assert
        verify(orderService).deleteOrder(orderId);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void createOrder() throws UserNotFoundException {
        // Arrange
        OrderDto orderDto = new OrderDto();
        Order order = new Order();

        when(orderMapper.mapToOrder(orderDto)).thenReturn(order);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.createOrder(orderDto);

        // Assert
        verify(orderService).saveOrder(order);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void editOrder() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        Order order = new Order();
        Order savedOrder = new Order();

        when(orderMapper.mapToOrder(orderDto)).thenReturn(order);
        when(orderService.saveOrder(order)).thenReturn(savedOrder);
        when(orderMapper.mapToOrderDto(savedOrder)).thenReturn(orderDto);

        // Act
        ResponseEntity<OrderDto> responseEntity = orderController.editOrder(orderDto);

        // Assert
        assertEquals(orderDto, responseEntity.getBody());
    }
}
