package ru.corpmarket.orderservice.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.corpmarket.orderservice.dto.OrderDto;
import ru.corpmarket.orderservice.dto.OrderStatusDto;
import ru.corpmarket.orderservice.dto.ProductServiceDto;
import ru.corpmarket.orderservice.model.Order;
import ru.corpmarket.orderservice.model.Status;
import ru.corpmarket.orderservice.repository.OrderRepository;
import ru.corpmarket.orderservice.rest.ProductServiceClient;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductServiceClient productServiceClient;

    private final OrderRepository orderRepository;


    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByProductId(UUID productId) {
        return orderRepository.findAllByProductId(productId);
    }
    public List<Order> findAllByConsumerId(UUID consumerId) {
        return orderRepository.findAllByConsumerId(consumerId);
    }

    public Order findById(UUID id) throws NotFoundException {
        return orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s is not found", id)));
    }
    @Transactional
    public Order saveOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setProductId(orderDto.getProductId());
        order.setCount(orderDto.getCount());
        order.setConsumerId(orderDto.getConsumerId());
        order.setStatus(Status.CREATED);
        order.setOrderDate(new Date());
        order.setProductType(orderDto.getProductType());
        order.setPrice(orderDto.getPrice() * orderDto.getCount());
        order.setTitle(orderDto.getTitle());
        try {
            productServiceClient.updateProductToOrderCount(
                    new ProductServiceDto(
                        order.getProductId(),
                        order.getCount()
                    )
            );
            return orderRepository.save(order);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Order updateOrderStatus(UUID id, OrderStatusDto orderStatusDto) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s id not found", id)));
        order.setStatus(orderStatusDto.getStatus());
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) throws NotFoundException{
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s id not found", id)));
        System.out.println("deleted order: " + order);
        orderRepository.deleteById(id);
    }
}
