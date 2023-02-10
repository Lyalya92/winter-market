package ru.geekbrains.winter_market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.winter_market.api.ProductDto;
import ru.geekbrains.winter_market.core.converters.ProductConverter;
import ru.geekbrains.winter_market.core.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> findAllProducts(@RequestParam (defaultValue = "1", name = "p") Integer page,
                                            @RequestParam (defaultValue = "10", name = "p_size")Integer pageSize,
                                            @RequestParam(name = "min_price", required = false) Integer minPrice,
                                            @RequestParam(name = "max_price", required = false) Integer maxPrice,
                                            @RequestParam(name = "title_part", required = false) String partTitle
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAllProducts(page - 1, pageSize, minPrice, maxPrice, partTitle)
                                                .map(productConverter::entityToDto);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestBody ProductDto productDto) {
        productService.addNewProduct(productDto);
    }
}
