package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {
    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getCart(),
                orderDto.getFlights()
        );
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getCart(),
                order.getFlights()
        );
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public List<Order> mapToOrderList(final List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(this::mapToOrder)
                .collect(Collectors.toList());
    }
}
