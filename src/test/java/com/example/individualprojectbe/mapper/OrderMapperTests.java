package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderMapperTests {

    private OrderMapper orderMapper;
    private OrderDto orderDto;
    private Order order;

    @BeforeEach
    void setUp() {
        orderMapper = Mappers.getMapper(OrderMapper.class);

        order = new Order(1L, 101L, 201L, List.of(301L, 302L));
        orderDto = new OrderDto(1L, 101L, 201L, List.of(301L, 302L));
    }

    @Test
    void mapToOrder() {
        Order mappedOrder = orderMapper.mapToOrder(orderDto);

        assertNotNull(mappedOrder);
        assertEquals(orderDto.getId(), mappedOrder.getId());
        assertEquals(orderDto.getCartId(), mappedOrder.getCartId());
        assertEquals(orderDto.getUserId(), mappedOrder.getUserId());
        assertEquals(orderDto.getFlights(), mappedOrder.getFlights());
    }

    @Test
    void mapToOrderDto() {
        OrderDto mappedOrderDto = orderMapper.mapToOrderDto(order);

        assertNotNull(mappedOrderDto);
        assertEquals(order.getId(), mappedOrderDto.getId());
        assertEquals(order.getCartId(), mappedOrderDto.getCartId());
        assertEquals(order.getUserId(), mappedOrderDto.getUserId());
        assertEquals(order.getFlights(), mappedOrderDto.getFlights());
    }

    @Test
    void mapToOrderDtoList() {
        List<Order> orderList = List.of(order);
        List<OrderDto> mappedOrderDtoList = orderMapper.mapToOrderDtoList(orderList);

        assertNotNull(mappedOrderDtoList);
        assertEquals(1, mappedOrderDtoList.size());
    }

    @Test
    void mapToOrderList() {
        List<OrderDto> orderDtoList = List.of(orderDto);
        List<Order> mappedOrderList = orderMapper.mapToOrderList(orderDtoList);

        assertNotNull(mappedOrderList);
        assertEquals(1, mappedOrderList.size());
    }
}
