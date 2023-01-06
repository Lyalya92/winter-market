package ru.geekbrains.winter_market.carts.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.api.CartDto;
import ru.geekbrains.winter_market.carts.model.Cart;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto (Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setProducts(cart.getProducts()
                .stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()));
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
}
