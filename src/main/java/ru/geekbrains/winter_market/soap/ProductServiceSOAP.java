package ru.geekbrains.winter_market.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.winter_market.repositories.ProductRepository;
import ru.geekbrains.winter_market.soap.products.Product;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceSOAP {
    private final ProductRepository productRepository;

    public static final Function<ru.geekbrains.winter_market.entities.Product, Product> functionEntityToSoap = pe -> {
        Product p = new Product();
        p.setId(pe.getId());
        p.setTitle(pe.getTitle());
        p.setPrice(pe.getPrice());
        return p;
    };

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).map(functionEntityToSoap).get();
    }

}
