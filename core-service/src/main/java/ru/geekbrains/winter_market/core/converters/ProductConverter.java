package ru.geekbrains.winter_market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getTitle());
        return productDto;
    }

}
