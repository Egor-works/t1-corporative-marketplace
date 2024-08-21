package ru.corpmarket.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.corpmarket.productservice.model.Product;
import ru.corpmarket.productservice.model.Size;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    @Query("select p from Product p where " +
            "((:colors) is null or p.color in (:colors)) " +
            "and ((:sizes) is null or p.size in (:sizes)) " +
            "and ((:price) is null or p.price <= :price) ")
    Optional<List<Product>> findAllByQuery(
            @Param("name") String title,
            @Param("colors") List<String> color,
            @Param("sizes") List<Size> size,
            @Param("price") Long price);

    @Query("SELECT DISTINCT p.color FROM  p")
    List<String> findDistinctColors();

    List<Product> findAllByColor(String color);
    List<Product> findAllBySize(Size size);
    List<Product> findAllByColorAndSize(String color, Size size);

    @Query("SELECT DISTINCT p.brand FROM Product p")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
}
