package com.example.individualprojectbe.service;

import com.example.individualprojectbe.domain.Order;
import com.example.individualprojectbe.exception.OrderNotFoundException;
import com.example.individualprojectbe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order saveOrder(final Order Order) {
        return repository.save(Order);
    }

    public Order getOrder(final Long id) throws OrderNotFoundException {
        return repository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public void deleteOrder(final Long id) {
        repository.deleteById(id);
    }
}
