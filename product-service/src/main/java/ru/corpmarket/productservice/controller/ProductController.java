package ru.corpmarket.productservice.controller;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.corpmarket.productservice.dto.CreateOrderDto;
import ru.corpmarket.productservice.dto.ProductDto;
import ru.corpmarket.productservice.exception.RunOutProductException;
import ru.corpmarket.productservice.model.Product;
import ru.corpmarket.productservice.service.MinioService;
import ru.corpmarket.productservice.service.ProductService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ProductController {

    private final ProductService productService;
    @Autowired
    private MinioService minioService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing() throws UnknownHostException {
        return ResponseEntity.ok("ping " + InetAddress.getLocalHost().getHostAddress());
    }

    @GetMapping(value = "/{productId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable UUID productId)
            throws NotFoundException {
        return ResponseEntity.ok(productService.findByProductId(productId));
    }

    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductByName(@RequestParam("productName") String productName)
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



    @PostMapping("/photo/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = minioService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/photo/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = minioService.downloadFile(fileName);
            byte[] fileBytes = IOUtils.toByteArray(fileStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
