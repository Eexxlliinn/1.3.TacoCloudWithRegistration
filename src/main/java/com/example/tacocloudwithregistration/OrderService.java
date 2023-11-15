package com.example.tacocloudwithregistration;

import com.example.tacocloudwithjpa.data.Taco;
import com.example.tacocloudwithjpa.data.TacoOrder;
import com.example.tacocloudwithjpa.repositories.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public <S extends TacoOrder> S save(S entity) {
        S savedOrder = orderRepository.save(entity);
        for (Taco taco : savedOrder.getTacos()) {
            taco.setTacoOrder(savedOrder);
        }
        savedOrder = orderRepository.save(entity);
        return savedOrder;
    }
}
