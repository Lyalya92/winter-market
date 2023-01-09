package ru.geekbrains.winter_market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.converters.ProductConverter;
import ru.geekbrains.winter_market.core.entities.Product;
import ru.geekbrains.winter_market.core.repositories.ProductRepository;
import ru.geekbrains.winter_market.core.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    public void addNewProduct(ProductDto productDto) {
        if (productDto != null){
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
        }
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductDto getProductById(Long id) {
        return productConverter.entityToDto(findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт с id " + id + " не найден")));
    }
}
