package ru.geekbrains.winter_market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.winter_market.entities.Order;
import ru.geekbrains.winter_market.entities.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUser(User user);
}

