package ru.corpmarket.managerservice.service;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import ru.corpmarket.managerservice.dto.ManagerDto;
import ru.corpmarket.managerservice.exception.NotUniqueEmailException;
import ru.corpmarket.managerservice.model.Manager;
import ru.corpmarket.managerservice.repository.ManagerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Manager findById(UUID id) throws NotFoundException {
        return managerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
    }

    public Manager findByEmail(String email) throws NotFoundException {
        return managerRepository.findManagerByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format("User with email %s is not found", email)));
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager saveManager(ManagerDto managerDto) throws NotUniqueEmailException {
        try {
            Manager manager = new Manager();
            manager.setEmail(managerDto.getEmail());
            manager.setPassword(bCryptPasswordEncoder.encode(managerDto.getPassword()));
            manager.setName(managerDto.getName());
            manager.setPhoneNumber(managerDto.getPhoneNumber());
            manager.setCoinsId(managerDto.getCoinsId());
            return managerRepository.save(manager);
        }
        catch (Exception e) {
            throw new NotUniqueEmailException(
                    String.format("User with email %s is already exists", managerDto.getEmail()));
        }
    }

    public Manager updateManager(UUID id, ManagerDto managerDto) throws NotFoundException {
        Manager manager = managerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));


        if (managerDto.getEmail() != null && !managerDto.getEmail().isEmpty()) {
            manager.setEmail(managerDto.getEmail());
        }
        if (managerDto.getPassword() != null && !managerDto.getPassword().isEmpty()) {
            manager.setPassword(bCryptPasswordEncoder.encode(managerDto.getPassword()));
        }
        if (managerDto.getPhoneNumber() != null) {
            manager.setPhoneNumber(managerDto.getPhoneNumber());
        }
        if (managerDto.getCoinsId() != null) {
            manager.setCoinsId(managerDto.getCoinsId());
        }

        return managerRepository.save(manager);
    }

    public void deleteManager(UUID id) throws NotFoundException {
        Manager manager = managerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
        managerRepository.delete(manager);
    }
}
