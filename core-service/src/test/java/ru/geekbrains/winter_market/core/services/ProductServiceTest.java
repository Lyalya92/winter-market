package ru.geekbrains.winter_market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.entities.Product;
import ru.geekbrains.winter_market.core.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductService productService;


    @Test
    void addNewProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("TestTitle");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setCategory("Еда");
        Product savedProduct = productService.addNewProduct(productDto);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(productDto.getTitle(), savedProduct.getTitle());
        Assertions.assertEquals(productDto.getPrice(), savedProduct.getPrice());
        Assertions.assertEquals(productDto.getCategory(), savedProduct.getCategory().getTitle());
    }

    @Test
    void deleteProductById() {
        ProductDto product = productService.getProductById(1L);
        Assertions.assertNotNull(product);
        productService.deleteProductById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

}