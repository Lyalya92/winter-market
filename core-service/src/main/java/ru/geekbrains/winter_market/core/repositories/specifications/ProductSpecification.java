package ru.geekbrains.winter_market.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.winter_market.core.entities.Product;

public class ProductSpecification {
    public static Specification<Product> priceGreaterOrEqualThan(Integer price) {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price)));
    }

    public static Specification<Product> priceLessOrEqualThan(Integer price) {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price)));
    }

    public static Specification<Product> titleLike(String partTitle) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format(("%%%s%%"), partTitle)));
    }
}
