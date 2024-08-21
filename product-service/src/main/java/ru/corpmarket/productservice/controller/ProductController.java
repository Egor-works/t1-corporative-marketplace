package ru.corpmarket.productservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.productservice.config.Swagger2Config;
import ru.corpmarket.productservice.dto.ProductDto;
import ru.corpmarket.productservice.model.Product;
import ru.corpmarket.productservice.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {Swagger2Config.TAG_PRODUCT})
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{productId}", produces = "application/json")
    @Operation(summary = "Get product by productId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Product> getProduct(@Parameter(description = "Product id", required = true, example = "10")
                                                     @PathVariable UUID productId)
            throws NotFoundException {
        return ResponseEntity.ok(productService.findByProductId(productId));
    }

    @GetMapping(value = "/catalog", produces = "application/json")
    @Operation(summary = "Get all product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<List<Product>> getAll() throws NotFoundException {
        return ResponseEntity.ok(productService.findAllProduct());
    }


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create clothes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Product> saveClothes(@Parameter(description = "Product dto", required = true)
                                                           @RequestBody ProductDto productDto) throws NotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.saveProduct(productDto));
    }

    @PatchMapping(value = "/{productId}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update product by productId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Product> updateClothes(@Parameter(description = "Product id", required = true, example = "10")
                                                 @PathVariable UUID productId,
                                                 @Parameter(description = "Clothes dto", required = true)
                                                 @RequestBody ProductDto productDto) throws NotFoundException {
        return ResponseEntity.ok(productService.updateProduct( productId, productDto));
    }


    @DeleteMapping(value = "/{productId}", produces = "application/json")
    @Operation(summary = "Delete product by productId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<?> deleteClothes(
                                           @Parameter(description = "Product id", required = true, example = "10")
                                           @PathVariable UUID productId)
            throws NotFoundException {
        System.out.println(productId);
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
