package ru.geekbrains.winter_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.converters.CartItemConverter;
import ru.geekbrains.winter_market.dtos.CartDto;
import ru.geekbrains.winter_market.dtos.CartItemDto;
import ru.geekbrains.winter_market.utils.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final CartItemConverter cartItemConverter;
    private Cart cart;


    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setProducts(new ArrayList<>());
    }

    public CartDto getCart() {
        List<CartItemDto> items = cart.getProducts().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList());
        return new CartDto(items, cart.getTotalPrice());
    }

    public void addProductToCart(Long productId) {
          cart.add(productService.getProductById(productId));
    }

    public void deleteProductFromCart(Long id) {
        cart.delete(id);
    }

    public void changeProductQuantity(Long id, Integer delta) {
        cart.changeProductQuantity(id, delta);
    }

    public void clearCart() {
        cart.clear();
    }
}
