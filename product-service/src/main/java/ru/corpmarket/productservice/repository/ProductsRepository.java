package ru.corpmarket.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.corpmarket.productservice.model.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);

//    List<Product> findAllByColor(String color);
//    List<Product> findAllBySize(Size size);
//    List<Product> findAllByColorAndSize(String color, Size size);

}
