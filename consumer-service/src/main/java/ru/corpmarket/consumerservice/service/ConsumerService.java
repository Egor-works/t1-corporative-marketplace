package ru.corpmarket.consumerservice.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.corpmarket.consumerservice.dto.ConsumerDto;
import ru.corpmarket.consumerservice.exception.NotUniqueEmailException;
import ru.corpmarket.consumerservice.model.Consumer;
import ru.corpmarket.consumerservice.repository.ConsumerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Consumer findById(UUID id) throws NotFoundException {
        return consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
    }

    public Consumer findByEmail(String email) throws NotFoundException {
        return consumerRepository.findCustomerByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format("User with email %s is not found", email)));
    }

    public List<Consumer> getAll() {
        return consumerRepository.findAll();
    }

    public Consumer saveCustomer(ConsumerDto consumerDto) throws NotUniqueEmailException {
        try {
            Consumer customer = new Consumer();
            customer.setEmail(consumerDto.getEmail());
            customer.setPassword(bCryptPasswordEncoder.encode(consumerDto.getPassword()));
            customer.setName(consumerDto.getName());
            customer.setPhoneNumber(consumerDto.getPhoneNumber());
            customer.setCoins(consumerDto.getCoins());
            return consumerRepository.save(customer);
        }
        catch (Exception e) {
            throw new NotUniqueEmailException(
                    String.format("User with email %s is already exists", consumerDto.getEmail()));
        }
    }

    public Consumer updateCustomer(UUID id, ConsumerDto updatedCustomer) throws NotFoundException {
        Consumer consumer = consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));


        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
            consumer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPassword() != null && !updatedCustomer.getPassword().isEmpty()) {
            consumer.setPassword(bCryptPasswordEncoder.encode(updatedCustomer.getPassword()));
        }
        if (updatedCustomer.getPhoneNumber() != null) {
            consumer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        }
        if (updatedCustomer.getCoins() != null) {
            consumer.setCoins(updatedCustomer.getCoins());
        }

        return consumerRepository.save(consumer);
    }

    public void deleteCustomer(UUID id) throws NotFoundException {
        Consumer consumer = consumerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id %s is not found", id)));
        consumerRepository.delete(consumer);
    }

}
