package ru.geekbrains.winter_market.api;


import java.math.BigDecimal;
import java.util.List;

public class CartDto {

    private List<CartItemDto> products;

    private BigDecimal totalPrice;

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
        this.products = items;
        this.totalPrice = totalPrice;
    }

    public List<CartItemDto> getProducts() {
        return products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setProducts(List<CartItemDto> products) {
        this.products = products;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
