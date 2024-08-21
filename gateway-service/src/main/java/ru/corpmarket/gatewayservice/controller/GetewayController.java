package ru.corpmarket.gatewayservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetewayController {
    @GetMapping(value = "/ping", produces = "application/json")
    public ResponseEntity<String> getCustomer(){
        return ResponseEntity.ok("pong");
    }
}
