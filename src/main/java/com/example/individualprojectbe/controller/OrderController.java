package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import com.example.individualprojectbe.exception.OrderNotFoundException;
import com.example.individualprojectbe.mapper.OrderMapper;
import com.example.individualprojectbe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orderMapper.mapToOrderDtoList(orders));
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.getOrder(orderId)));
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.mapToOrder(orderDto);
        orderService.saveOrder(order);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{orderId}")
    public ResponseEntity<OrderDto> editOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.mapToOrder(orderDto);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(savedOrder));
    }
}
