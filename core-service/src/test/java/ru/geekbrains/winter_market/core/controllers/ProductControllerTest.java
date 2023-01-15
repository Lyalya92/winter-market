package ru.geekbrains.winter_market.core.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.winter_market.core.services.ProductService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductControllerTest {

    @Autowired
    ProductService productService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void getProductById() {
        ProductDto testProduct = productService.getProductById(1L);

        ProductDto testProductByHttp = webTestClient.get()
                .uri("/api/v1/products/" + testProduct.getId())
                .exchange()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(testProduct.getId(), testProductByHttp.getId());
        Assertions.assertEquals(testProduct.getTitle(), testProductByHttp.getTitle());
        Assertions.assertEquals(testProduct.getPrice(), testProductByHttp.getPrice());

        webTestClient.get()
                .uri("/api/v1/products/150")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteProduct() {
        ProductDto testProduct = productService.getProductById(1L);
        Assertions.assertNotNull(testProduct);
        webTestClient.delete()
                .uri("/api/v1/products/1")
                .exchange();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void addNewProduct() {
        ProductDto testNewProduct = new ProductDto();
        testNewProduct.setTitle("TestProduct");
        testNewProduct.setPrice(BigDecimal.ONE);
        testNewProduct.setCategory("Еда");

        webTestClient.post()
                .uri("/api/v1/products")
                .body(BodyInserters.fromObject(testNewProduct))
                .exchange()
                .expectStatus().isCreated();

        ProductDto savedProduct = productService.getProductByTitle(testNewProduct.getTitle());
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(savedProduct.getCategory(), testNewProduct.getCategory());
    }
}