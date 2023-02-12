package ru.geekbrains.winter_market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.winter_market.api.CartDto;
import ru.geekbrains.winter_market.api.StringResponse;
import ru.geekbrains.winter_market.carts.converters.CartConverter;
import ru.geekbrains.winter_market.carts.services.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateGuestCartID() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{guestCartId}")
    public CartDto getCart(@RequestHeader(name = "username", required = false) String username,
                           @PathVariable String guestCartId) {
        String targetUuid = getCartUuid(username, guestCartId);
        return cartConverter.entityToDto(cartService.getCart(targetUuid));
    }

    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username,
                                 @PathVariable String guestCartId,
                                 @PathVariable Long productId) {
        String targetUuid = getCartUuid(username, guestCartId);
        cartService.addProductToCart(targetUuid, productId);
    }

    @GetMapping("/{guestCartId}/delete/{productId}")
    public void deleteProductFromCart(@RequestHeader(name = "username", required = false) String username,
                                      @PathVariable String guestCartId,
                                      @PathVariable Long productId) {
        String targetUuid = getCartUuid(username, guestCartId);
        cartService.deleteProductFromCart(targetUuid, productId);
    }

    @GetMapping("/change_quantity")
    public void changeProductQuantityICart(@RequestHeader(name = "username", required = false) String username,
                                           @RequestParam String uuid,
                                           @RequestParam Long id,
                                           @RequestParam Integer delta) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.changeProductQuantity(targetUuid, id, delta);
    }

    @GetMapping("/{guestCartId}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String guestCartId) {
        String targetUuid = getCartUuid(username, guestCartId);
        cartService.clearCart(targetUuid);
    }

    @GetMapping("/{guestCartId}/merge_cart")
    public void mergeGuestCart(@RequestHeader(name = "username", required = false) String username,
                               @PathVariable String guestCartId) {
        cartService.mergeCarts(username, guestCartId);
    }

    private String getCartUuid(String username, String guestCartId) {
        if (username != null) return username;
        return guestCartId;
    }
}
