package com.example.tacocloudwithregistration;


import com.example.tacocloudwithregistration.data.Taco;
import com.example.tacocloudwithregistration.data.TacoOrder;
import com.example.tacocloudwithregistration.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
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
        log.info("Order saved with id: {}", entity.getId());
        for (Taco taco : entity.getTacos()) {
            log.info("Taco id: {}", taco.getId());
        }
        return savedOrder;
    }

}
