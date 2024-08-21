package ru.corpmarket.consumerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.corpmarket.consumerservice.model.Consumer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, UUID> {
    Optional<Consumer> findCustomerByEmail(String email);

}
