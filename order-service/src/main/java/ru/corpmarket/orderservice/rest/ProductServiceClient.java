package ru.corpmarket.orderservice.rest;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import ru.corpmarket.orderservice.dto.ProductServiceDto;

@FeignClient(name = "product-service")

public interface ProductServiceClient {

    @RequestMapping(value = "products/ordered", method = RequestMethod.POST)
    void updateProductToOrderCount(ProductServiceDto productServiceDto);

}
