package ru.corpmarket.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corpmarket.adminservice.model.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);
}
