package ru.geekbrains.winter_market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.api.CartDto;
import ru.geekbrains.winter_market.api.CartItemDto;
import ru.geekbrains.winter_market.api.OrderDto;
import ru.geekbrains.winter_market.core.converters.OrderConverter;
import ru.geekbrains.winter_market.core.entities.Order;
import ru.geekbrains.winter_market.core.entities.OrderItem;
import ru.geekbrains.winter_market.core.integrations.CartServiceIntegration;
import ru.geekbrains.winter_market.core.repositories.OrderRepository;
import ru.geekbrains.winter_market.core.exceptions.OrderNotFoundException;
import ru.geekbrains.winter_market.core.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public void createOrder(OrderDto orderDto) {
        CartDto cart = cartServiceIntegration.getCurrentCart(orderDto.getUsername());
        Order order = new Order();
        order.setUsername(orderDto.getUsername());
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setTotalPrice(cart.getTotalPrice());
        order.setItems(createOrderItemsList(cart.getProducts(), order));
        orderRepository.save(order);
        cartServiceIntegration.clear(orderDto.getUsername());
    }

    public List<OrderItem> createOrderItemsList (List<CartItemDto> cartItems, Order order) {
            return cartItems.stream().map(
                    cartItem -> new OrderItem(
                            productService.findById(cartItem.getProductId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Продукт с id " + cartItem.getProductId() + " не найден")),
                            order,
                            cartItem.getQuantity(),
                            cartItem.getPricePerProduct(),
                            cartItem.getPriceTotal())
            ).collect(Collectors.toList());
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }

    public OrderDto getOrderById (Long id) {
        return orderConverter.entityToDto(findOrder(id)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с id " + id + " не найден")));
    }

    public void updateOrder(Order order) {

    }
}
