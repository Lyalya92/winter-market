package ru.geekbrains.winter_market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.api.OrderDto;
import ru.geekbrains.winter_market.core.entities.Order;

@Component
public class OrderConverter {
    public OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        return orderDto;
    }
}
