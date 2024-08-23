package ru.corpmarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.corpmarket.orderservice.model.Order;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByConsumerId(UUID consumerId);
    List<Order> findAllByProductId(UUID productId);
}
