package ru.geekbrains.winter_market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.converters.ProductConverter;
import ru.geekbrains.winter_market.core.entities.Product;
import ru.geekbrains.winter_market.core.repositories.ProductRepository;
import ru.geekbrains.winter_market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.winter_market.core.repositories.specifications.ProductSpecification;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    public Page<Product> findAllProducts(Integer page, Integer pageSize, Integer minPrice, Integer maxPrice, String partTitle) {
        Specification<Product> specification = createSpecificationByFilters(minPrice, maxPrice, partTitle);
        return productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    public Specification<Product> createSpecificationByFilters(Integer minPrice, Integer maxPrice, String partTitle) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(ProductSpecification.priceGreaterOrEqualThan(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductSpecification.priceLessOrEqualThan(maxPrice));
        }
        if (partTitle != null) {
            specification = specification.and(ProductSpecification.titleLike(partTitle));
        }
        return specification;
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
