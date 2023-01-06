package ru.geekbrains.winter_market.carts.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.api.CartItemDto;
import ru.geekbrains.winter_market.carts.model.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto (CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItem.getProductId());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setPriceTotal(cartItem.getPriceTotal());
        return cartItemDto;
    }

}
