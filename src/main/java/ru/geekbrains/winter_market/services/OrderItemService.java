package ru.geekbrains.winter_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.entities.OrderItem;
import ru.geekbrains.winter_market.repositories.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void addItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }
}
