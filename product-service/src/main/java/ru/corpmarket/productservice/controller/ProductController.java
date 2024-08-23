package ru.corpmarket.productservice.controller;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.productservice.dto.CreateOrderDto;
import ru.corpmarket.productservice.dto.ProductDto;
import ru.corpmarket.productservice.exception.RunOutProductException;
import ru.corpmarket.productservice.model.Product;
import ru.corpmarket.productservice.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing(){
        return ResponseEntity.ok("pong");
    }

    @GetMapping(value = "/{productId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable UUID productId)
            throws NotFoundException {
        return ResponseEntity.ok(productService.findByProductId(productId));
    }

    @GetMapping(value = "/{productName}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> getProductByName(@PathVariable String productName)
            throws NotFoundException {
        return ResponseEntity.ok(productService.findByProductName(productName));
    }

    @GetMapping(value = "/catalog", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Product>> getAll() throws NotFoundException {
        return ResponseEntity.ok(productService.findAllProduct());
    }


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto) throws NotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.saveProduct(productDto));
    }

    @PostMapping(value = "/{productId}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable UUID productId,
                                                 @RequestBody ProductDto productDto) throws NotFoundException {
        return ResponseEntity.ok(productService.updateProduct( productId, productDto));
    }

    @PostMapping(value = "/ordered", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> updateProductToOrderCount(
                                                 @RequestBody CreateOrderDto createOrderDto) throws NotFoundException, RunOutProductException {
        return ResponseEntity.ok(productService.updateProductToOrderCount(createOrderDto));
    }


    @DeleteMapping(value = "/{productId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId)
            throws NotFoundException {
        System.out.println(productId);
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
