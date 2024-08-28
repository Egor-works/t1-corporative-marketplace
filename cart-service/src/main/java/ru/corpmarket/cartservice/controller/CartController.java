package ru.corpmarket.cartservice.controller;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.cartservice.dto.CartDto;
import ru.corpmarket.cartservice.dto.ProductCountDto;
import ru.corpmarket.cartservice.model.Cart;
import ru.corpmarket.cartservice.service.CartService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CartController {
    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing() throws UnknownHostException {
        return ResponseEntity.ok("ping " + InetAddress.getLocalHost().getHostAddress());
    }

    private final CartService cartService;

    @GetMapping(value = "/{consumerId}", produces = "application/json")
    public ResponseEntity<List<Cart>> getCart(@PathVariable UUID consumerId) {
        return ResponseEntity.ok(cartService.findAllByConsumerId(consumerId));
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cart> saveCart(@RequestBody CartDto cartDto) throws IllegalArgumentException {
        return ResponseEntity.ok(cartService.saveCart(cartDto));
    }

    @PostMapping(value = "/{cartId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cart> updateCart(
                                           @PathVariable UUID cartId,
                                           @RequestBody ProductCountDto productCountDto)
            throws IllegalArgumentException, NotFoundException {
        return ResponseEntity.ok(cartService.updateCart(cartId, productCountDto));
    }

    @DeleteMapping(value = "/{cartId}", produces = "application/json")
    public ResponseEntity<?> deleteCart(@PathVariable UUID cartId) throws NotFoundException {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

}