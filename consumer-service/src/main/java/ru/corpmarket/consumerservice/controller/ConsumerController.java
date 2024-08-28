package ru.corpmarket.consumerservice.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.consumerservice.dto.ConsumerDto;
import ru.corpmarket.consumerservice.dto.EncodedPasswordDto;
import ru.corpmarket.consumerservice.exception.NotUniqueEmailException;
import ru.corpmarket.consumerservice.model.Consumer;
import ru.corpmarket.consumerservice.service.ConsumerService;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ConsumerController {

    private final ConsumerService customerService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing() throws UnknownHostException {
        return ResponseEntity.ok("ping " + InetAddress.getLocalHost().getHostAddress());
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Consumer>> getAll(){
        return ResponseEntity.ok(customerService.getAll());
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Consumer> createCustomer(@RequestBody ConsumerDto customerDto) throws NotUniqueEmailException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.saveCustomer(customerDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Consumer> getCustomer(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Consumer> updateCustomer(@PathVariable UUID id,
                                                   @RequestBody ConsumerDto updatedCustomer)
            throws NotFoundException {
        return ResponseEntity.ok(customerService.updateCustomer(id, updatedCustomer));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) throws NotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/email/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Consumer> getCustomerByEmail(@RequestParam("e") String email) throws NotFoundException {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<EncodedPasswordDto> getEncodedPasswordByEmail(@RequestParam("email") String email)
            throws NotFoundException {
        return ResponseEntity.ok(new EncodedPasswordDto(customerService.findByEmail(email).getPassword()));
    }
}
