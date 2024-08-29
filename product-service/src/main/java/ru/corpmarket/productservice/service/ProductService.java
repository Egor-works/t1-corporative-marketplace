package ru.corpmarket.productservice.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.corpmarket.productservice.dto.CreateOrderDto;
import ru.corpmarket.productservice.dto.ProductDto;

import ru.corpmarket.productservice.exception.RunOutProductException;
import ru.corpmarket.productservice.model.Product;
import ru.corpmarket.productservice.repository.ProductsRepository;

import java.util.List;
import java.util.Objects;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductsRepository productRepository;


    public Product findByProductId(UUID id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Product with id %s is not found", id)));
    }

    public List<Product> findByProductName(String name) throws NotFoundException{
        return productRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(String.format("Product with name %s is not found", name)));
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public Product saveProduct(ProductDto productDto) throws NotFoundException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setColor(productDto.getColor());
        product.setSize(productDto.getSize());
        product.setCount(productDto.getCount());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setBrand(productDto.getBrand());

        return productRepository.save(product);
    }

    public Product updateProduct(UUID productId, ProductDto productDto) throws NotFoundException {
        Product clothes = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException(String.format("Clothes with id %s is not found", productId)));

        if (Objects.nonNull(productDto.getName()) && !productDto.getName().isEmpty()) {
            clothes.setName(productDto.getName());
        }
        if (Objects.nonNull(productDto.getDescription()) && !productDto.getDescription().isEmpty()) {
            clothes.setDescription(productDto.getDescription());
        }
        if (Objects.nonNull(productDto.getColor()) && !productDto.getColor().isEmpty()) {
            clothes.setColor(productDto.getColor());
        }
        if (Objects.nonNull(productDto.getSize())) {
            clothes.setSize(productDto.getSize());
        }
        if (Objects.nonNull(productDto.getCount()) && productDto.getCount() >= 0) {
            clothes.setCount(productDto.getCount());
        }
        if (Objects.isNull(productDto.getPrice()) || productDto.getPrice() > 0) {
            clothes.setPrice(productDto.getPrice());
        }
        if (Objects.nonNull(productDto.getCategory()) && !productDto.getCategory().isEmpty()) {
            clothes.setCategory(productDto.getCategory());
        }
        if (Objects.nonNull(productDto.getBrand()) && !productDto.getBrand().isEmpty()) {
            clothes.setBrand(productDto.getBrand());
        }


        return productRepository.save(clothes);
    }


    public Product updateProductToOrderCount(CreateOrderDto createOrderDto) throws NotFoundException, RunOutProductException {
        Product product = productRepository.findById(createOrderDto.getProductId()).orElseThrow(() ->
                new NotFoundException(String.format("Clothes with id %s is not found", createOrderDto.getProductId())));
        if (product.getCount() >= createOrderDto.getCount()) {
            product.setCount(product.getCount() - createOrderDto.getCount());
        }else throw new RunOutProductException("Product has done");
        return productRepository.save(product);
    }

    public void deleteProduct(UUID productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException(String.format("Clothes with id %s is not found", productId)));
        System.out.println(product.toString());
        productRepository.deleteById(productId);
    }



}