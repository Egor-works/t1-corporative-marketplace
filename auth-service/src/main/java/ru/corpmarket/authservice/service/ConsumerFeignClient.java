package ru.corpmarket.authservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.corpmarket.authservice.dto.EncodedPasswordDto;

@FeignClient(name = "consumer-service")
public interface ConsumerFeignClient {
    @GetMapping ("consumers/")
    ResponseEntity<EncodedPasswordDto> getEncodedPasswordByEmail(@RequestParam("email") String email);
}
