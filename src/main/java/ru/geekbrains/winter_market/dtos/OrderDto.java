package ru.geekbrains.winter_market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private String username;

    private List<OrderItemDto> items;

    private String address;

    private String phone;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;
}
