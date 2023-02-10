package ru.geekbrains.winter_market.carts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal priceTotal;

    public void changeQuantity(int delta) {
        quantity += delta;
        priceTotal = priceTotal.add(pricePerProduct.multiply(new BigDecimal(delta)));
    }
}
