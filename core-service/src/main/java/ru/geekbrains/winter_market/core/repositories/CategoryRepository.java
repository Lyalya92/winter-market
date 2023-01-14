package ru.geekbrains.winter_market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.winter_market.core.entities.Category;
import ru.geekbrains.winter_market.core.entities.Product;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Product, Long> {
    Optional<Category> findByTitle(String title);
}
