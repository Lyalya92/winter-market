package ru.geekbrains.winter_market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal priceTotal;
}
