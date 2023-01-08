package ru.geekbrains.winter_market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.carts.exceptions.ResourceNotFoundException;
import ru.geekbrains.winter_market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.winter_market.carts.model.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;



@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;

    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setProducts(new ArrayList<>());
    }

    public Cart getCart() {
      return cart;
    }

    public void addProductToCart(Long productId) {
          cart.add(productServiceIntegration.getProductById(productId)
                  .orElseThrow(() -> new ResourceNotFoundException("Продукт с id " + productId + " не найден")));
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
