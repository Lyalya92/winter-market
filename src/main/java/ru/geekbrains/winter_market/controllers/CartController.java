package ru.geekbrains.winter_market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.winter_market.dtos.CartDto;
import ru.geekbrains.winter_market.services.CartService;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public CartDto getCurrentCart() {
        return  cartService.getCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductToCart(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteProductFromCart(id);
    }

    @GetMapping("/change_quantity")
    public void changeProductQuantityICart(@RequestParam Long id, @RequestParam Integer delta) {
        cartService.changeProductQuantity(id, delta);
    }

    @DeleteMapping
    public void clearCart() {
        cartService.clearCart();
    }
}
