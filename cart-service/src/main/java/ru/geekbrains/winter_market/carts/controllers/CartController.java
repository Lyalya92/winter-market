package ru.geekbrains.winter_market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.winter_market.api.CartDto;
import ru.geekbrains.winter_market.carts.converters.CartConverter;
import ru.geekbrains.winter_market.carts.services.CartService;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCart());
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
