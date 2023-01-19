package ru.geekbrains.winter_market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    public List<ProductDto> findAllProducts(int page, int pageSize) {
         return productRepository.findAll(PageRequest.of(page, pageSize))
                .stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    public Product addNewProduct(ProductDto productDto) {
        if (productDto != null) {
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setCategory(categoryService.findByTitle(productDto.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена")));
            return productRepository.save(product);
        }
        return null;
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

    public ProductDto getProductByTitle(String title) {
        return productConverter.entityToDto(productRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт с названием " + title + " не найден")));
    }
}
