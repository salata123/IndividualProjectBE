package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.domain.OrderDto;
import com.example.individualprojectbe.exception.OrderNotFoundException;
import com.example.individualprojectbe.exception.UserNotFoundException;
import com.example.individualprojectbe.mapper.OrderMapper;
import com.example.individualprojectbe.service.OrderService;
import com.example.individualprojectbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;

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
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws UserNotFoundException {
        Order order = orderMapper.mapToOrder(orderDto);
        orderService.saveOrder(order);
        userService.addOrderToUserOrders(userService.getUser(order.getUserId()).getId(), order.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{orderId}")
    public ResponseEntity<OrderDto> editOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.mapToOrder(orderDto);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(savedOrder));
    }
}
