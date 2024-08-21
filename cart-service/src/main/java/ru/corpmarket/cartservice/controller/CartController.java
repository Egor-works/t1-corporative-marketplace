package ru.corpmarket.cartservice.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    @GetMapping(value = "/ping", produces = "application/json")
    public ResponseEntity<String> getCustomer(){
        return ResponseEntity.ok("pong");
    }
}