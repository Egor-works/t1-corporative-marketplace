package ru.corpmarket.adminservice.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.corpmarket.adminservice.dto.AdminDto;
import ru.corpmarket.adminservice.exception.NotUniqueEmailException;
import ru.corpmarket.adminservice.model.Admin;
import ru.corpmarket.adminservice.repository.AdminRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository consumerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Admin findById(UUID id) throws NotFoundException {
        return consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
    }

    public Admin findByEmail(String email) throws NotFoundException {
        return consumerRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format("User with email %s is not found", email)));
    }

    public List<Admin> getAll() {
        return consumerRepository.findAll();
    }

    public Admin saveAdmin(AdminDto adminDto) throws NotUniqueEmailException {
        try {
            Admin admin = new Admin();
            admin.setEmail(adminDto.getEmail());
            admin.setPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));
            admin.setName(adminDto.getName());
            admin.setPhoneNumber(adminDto.getPhoneNumber());
            admin.setCoinsId(adminDto.getCoinsId());
            return consumerRepository.save(admin);
        }
        catch (Exception e) {
            throw new NotUniqueEmailException(
                    String.format("User with email %s is already exists", adminDto.getEmail()));
        }
    }

    public Admin updateAdmin(UUID id, AdminDto updatedCustomer) throws NotFoundException {
        Admin admin = consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));


        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
            admin.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPassword() != null && !updatedCustomer.getPassword().isEmpty()) {
            admin.setPassword(bCryptPasswordEncoder.encode(updatedCustomer.getPassword()));
        }
        if (updatedCustomer.getPhoneNumber() != null) {
            admin.setPhoneNumber(updatedCustomer.getPhoneNumber());
        }
        if (updatedCustomer.getCoinsId() != null) {
            admin.setCoinsId(updatedCustomer.getCoinsId());
        }

        return consumerRepository.save(admin);
    }

    public void deleteAdmin(UUID id) throws NotFoundException {
        Admin admin = consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
        consumerRepository.delete(admin);
    }
}
