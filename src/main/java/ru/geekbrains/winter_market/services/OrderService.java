package ru.geekbrains.winter_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.converters.OrderConverter;
import ru.geekbrains.winter_market.dtos.CartItemDto;
import ru.geekbrains.winter_market.dtos.OrderDto;
import ru.geekbrains.winter_market.entities.Order;
import ru.geekbrains.winter_market.entities.OrderItem;
import ru.geekbrains.winter_market.entities.Product;
import ru.geekbrains.winter_market.entities.User;
import ru.geekbrains.winter_market.exceptions.OrderNotFoundException;
import ru.geekbrains.winter_market.exceptions.ResourceNotFoundException;
import ru.geekbrains.winter_market.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public void createOrder(OrderDto orderDto) {
        Order order = new Order();
        User user = userService.findByUsername(orderDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с ником " + orderDto.getUsername() + " не найден"));
        order.setUser(user);
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setTotalPrice(cartService.getCart().getTotalPrice());
        orderRepository.save(order);
        List<Order> orders = orderRepository.findByUser(user);
        createOrderItems(cartService.getCart().getProducts(), orders.get(orders.size()-1).getId());
    }

    public void createOrderItems (List <CartItemDto> cartItems, Long orderId) {
        for (CartItemDto i: cartItems) {
            Product product = productService.findById(i.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт с id " + i.getProductId() + " не найден"));
            int quantity = i.getQuantity();
            BigDecimal pricePerProduct = i.getPricePerProduct();
            BigDecimal priceTotal = i.getPriceTotal();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Ошибка при поиске заказа под номером " + orderId)));
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPricePerProduct(pricePerProduct);
            orderItem.setPrice(priceTotal);
            orderItemService.addItem(orderItem);
        }
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
