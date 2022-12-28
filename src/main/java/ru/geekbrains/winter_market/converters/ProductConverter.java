package ru.geekbrains.winter_market.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.dtos.ProductDto;
import ru.geekbrains.winter_market.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

}
