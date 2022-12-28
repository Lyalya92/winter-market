package ru.geekbrains.winter_market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

    private List<CartItemDto> products;

    private BigDecimal totalPrice;
}
