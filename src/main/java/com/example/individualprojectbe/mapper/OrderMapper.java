package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapToOrder(OrderDto orderDto);
    OrderDto mapToOrderDto(Order order);
    List<OrderDto> mapToOrderDtoList(List<Order> orderList);
    List<Order> mapToOrderList(List<OrderDto> orderDtoList);
}
