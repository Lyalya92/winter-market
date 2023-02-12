package ru.geekbrains.winter_market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.winter_market.carts.model.Cart;
import ru.geekbrains.winter_market.carts.model.CartItem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    @PostConstruct
    public void init() {
    }

    public Cart getCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart(new ArrayList<>()));
        }
        return  (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void addProductToCart(String uuid, Long productId) {
        execute(uuid, cart -> cart.add(productServiceIntegration.getProductById(productId)));
    }

    public void deleteProductFromCart(String uuid, Long productId) {
        execute(uuid, cart -> cart.delete(productId));
    }

    public void changeProductQuantity(String uuid, Long productId, Integer delta) {
          execute(uuid, cart -> cart.changeProductQuantity(productId, delta));
    }

    public void clearCart(String uuid) {
          execute(uuid, Cart::clear);
    }

    public void mergeCarts(String username, String uuid) {
        Cart guestCart = getCart(uuid);
        Cart userCart = getCart(cartPrefix + username);
        if (userCart.getProducts().size() == 0) {
            userCart.setProducts(guestCart.getProducts());
        } else {
            for (CartItem item : guestCart.getProducts()) {
               userCart.add(item);
            }
        }
        clearCart(uuid);
        redisTemplate.opsForValue().set(cartPrefix + username, userCart);
    }

    private void execute(String uuid, Consumer<Cart> cartOperation) {
        Cart cart = getCart(uuid);
        cartOperation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }
}
