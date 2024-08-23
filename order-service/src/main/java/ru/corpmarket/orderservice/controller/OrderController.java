package ru.corpmarket.orderservice.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corpmarket.orderservice.dto.OrderDto;
import ru.corpmarket.orderservice.dto.OrderStatusDto;
import ru.corpmarket.orderservice.model.Order;
import ru.corpmarket.orderservice.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/ping", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getPing(){
        return ResponseEntity.ok("pong");
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Order>> getAll(){
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.saveOrder(orderDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping(value = "/consumers/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrdersByConsumerId(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findAllByConsumerId(id));
    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrdersByProductId(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findAllByProductId(id));
    }


    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Order> updateOrderStatus(
                                                   @PathVariable UUID id,
                                                   @RequestBody OrderStatusDto orderStatusDto) throws NotFoundException {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatusDto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) throws NotFoundException {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

}
