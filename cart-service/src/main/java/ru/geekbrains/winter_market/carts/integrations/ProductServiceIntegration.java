package ru.geekbrains.winter_market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.carts.exceptions.ResourceNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                          clientResponse -> Mono.error(new ResourceNotFoundException("Продукт с id = " + id + " не найден"))
                        )
                .bodyToMono(ProductDto.class)
                .block();
    }

}
