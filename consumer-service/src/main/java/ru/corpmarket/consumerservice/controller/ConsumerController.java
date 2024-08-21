package ru.corpmarket.consumerservice.controller;

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
import ru.corpmarket.consumerservice.config.Swagger2Config;
import ru.corpmarket.consumerservice.dto.ConsumerDto;
import ru.corpmarket.consumerservice.dto.EncodedPasswordDto;
import ru.corpmarket.consumerservice.exception.NotUniqueEmailException;
import ru.corpmarket.consumerservice.model.Consumer;
import ru.corpmarket.consumerservice.service.ConsumerService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {Swagger2Config.TAG_CONSUMER})
public class ConsumerController {

    private final ConsumerService customerService;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create consumer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 409, message = "CONFLICT")
    })
    public ResponseEntity<Consumer> createCustomer(@Parameter(description = "Customer dto", required = true)
                                                   @RequestBody ConsumerDto customerDto) throws NotUniqueEmailException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.saveCustomer(customerDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get customer by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Consumer> getCustomer(@Parameter(description = "Customer id", required = true, example = "123")
                                                @PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update customer by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Consumer> updateCustomer(@Parameter(description = "Customer id", required = true, example = "123")
                                                   @PathVariable UUID id,
                                                   @Parameter(description = "Customer dto", required = true)
                                                   @RequestBody ConsumerDto updatedCustomer)
            throws NotFoundException {
        return ResponseEntity.ok(customerService.updateCustomer(id, updatedCustomer));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete customer by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<?> deleteCustomer(@Parameter(description = "Customer id", required = true, example = "123")
                                            @PathVariable UUID id) throws NotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get customer by email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    public ResponseEntity<Consumer> getCustomerByEmail(@Parameter(description = "Customer email", required = true, example = "ivan@company.com")
                                                       @RequestParam("e") String email) throws NotFoundException {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }

    @GetMapping
    @ApiIgnore
    public ResponseEntity<EncodedPasswordDto> getEncodedPasswordByEmail(@RequestParam("email") String email)
            throws NotFoundException {
        return ResponseEntity.ok(new EncodedPasswordDto(customerService.findByEmail(email).getPassword()));
    }
}
