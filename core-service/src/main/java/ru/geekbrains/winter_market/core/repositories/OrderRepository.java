package ru.geekbrains.winter_market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.winter_market.core.entities.Order;
import ru.geekbrains.winter_market.core.entities.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUser(User user);
}

