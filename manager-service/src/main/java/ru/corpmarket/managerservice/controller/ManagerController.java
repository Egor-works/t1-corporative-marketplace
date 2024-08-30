package ru.corpmarket.managerservice.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.managerservice.dto.EncodedPasswordDto;
import ru.corpmarket.managerservice.dto.ManagerDto;
import ru.corpmarket.managerservice.exception.NotUniqueEmailException;
import ru.corpmarket.managerservice.model.Manager;
import ru.corpmarket.managerservice.service.ManagerService;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing() throws UnknownHostException {
        return ResponseEntity.ok("ping " + InetAddress.getLocalHost().getHostAddress());
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Manager>> getAll(){
        return ResponseEntity.ok(managerService.getAll());
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Manager> createManager(@RequestBody ManagerDto managerDto) throws NotUniqueEmailException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(managerService.saveManager(managerDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Manager> getManager(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(managerService.findById(id));
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Manager> updateCustomer(@PathVariable UUID id,
                                                   @RequestBody ManagerDto managerDto)
            throws NotFoundException {
        return ResponseEntity.ok(managerService.updateManager(id, managerDto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteManager(@PathVariable UUID id) throws NotFoundException {
        managerService.deleteManager(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/email/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Manager> getCustomerByEmail(@RequestParam("e") String email) throws NotFoundException {
        return ResponseEntity.ok(managerService.findByEmail(email));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<EncodedPasswordDto> getEncodedPasswordByEmail(@RequestParam("email") String email)
            throws NotFoundException {
        return ResponseEntity.ok(new EncodedPasswordDto(managerService.findByEmail(email).getPassword()));
    }
}

