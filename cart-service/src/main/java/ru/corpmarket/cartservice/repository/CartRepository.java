package ru.corpmarket.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.corpmarket.cartservice.model.Cart;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findAllByConsumerId(UUID consumerId);
    List<Cart> findAllByProductId(UUID productId);
//    List<Cart> findAllByProductType(ProductType productType);
}
