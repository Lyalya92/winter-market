package ru.geekbrains.winter_market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.winter_market.api.OrderData;
import ru.geekbrains.winter_market.api.OrderDto;
import ru.geekbrains.winter_market.core.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
   private final OrderService orderService;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public void createOrder (@RequestHeader String username, @RequestBody OrderData orderData) {

       OrderDto orderDto = new OrderDto();
       orderDto.setUsername(username);
       orderDto.setPhone(orderData.getPhone());
       orderDto.setAddress(orderData.getAddress());
       orderService.createOrder(orderDto);
   }
}
