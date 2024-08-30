package ru.corpmarket.adminservice.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.corpmarket.adminservice.dto.AdminDto;
import ru.corpmarket.adminservice.dto.EncodedPasswordDto;
import ru.corpmarket.adminservice.exception.NotUniqueEmailException;
import ru.corpmarket.adminservice.model.Admin;
import ru.corpmarket.adminservice.service.AdminService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing() throws UnknownHostException {
        return ResponseEntity.ok("ping " + InetAddress.getLocalHost().getHostAddress());
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Admin>> getAll(){
        return ResponseEntity.ok(adminService.getAll());
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Admin> createCustomer(@RequestBody AdminDto adminDto) throws NotUniqueEmailException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.saveAdmin(adminDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Admin> getCustomer(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(adminService.findById(id));
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Admin> updateCustomer(@PathVariable UUID id,
                                                   @RequestBody AdminDto updatedAdmin)
            throws NotFoundException {
        return ResponseEntity.ok(adminService.updateAdmin(id, updatedAdmin));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteAdmin(@PathVariable UUID id) throws NotFoundException {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/email/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Admin> getAdminByEmail(@RequestParam("e") String email) throws NotFoundException {
        return ResponseEntity.ok(adminService.findByEmail(email));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<EncodedPasswordDto> getEncodedPasswordByEmail(@RequestParam("email") String email)
            throws NotFoundException {
        return ResponseEntity.ok(new EncodedPasswordDto(adminService.findByEmail(email).getPassword()));
    }
}
