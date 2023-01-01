package ru.geekbrains.winter_market.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.dtos.OrderDto;
import ru.geekbrains.winter_market.dtos.ProductDto;
import ru.geekbrains.winter_market.entities.Order;
import ru.geekbrains.winter_market.entities.Product;

@Component
public class OrderConverter {
    public OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        return orderDto;
    }
}
