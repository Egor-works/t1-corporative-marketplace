package ru.corpmarket.cartservice.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.corpmarket.cartservice.dto.CartDto;
import ru.corpmarket.cartservice.dto.ProductCountDto;
import ru.corpmarket.cartservice.model.Cart;
import ru.corpmarket.cartservice.repository.CartRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> findAllByConsumerId(UUID consumerId) {
        return cartRepository.findAllByConsumerId(consumerId);
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public List<Cart> findAllByProductId(UUID productId) {
        return cartRepository.findAllByProductId(productId);
    }

    public Cart findById(UUID cartId) throws NotFoundException {
        return cartRepository.findById(cartId).orElseThrow(() ->
                new NotFoundException(String.format("Cart with id %s is not found", cartId)));
    }

    public Cart saveCart(CartDto cartDto) throws IllegalArgumentException {
        System.out.println(cartDto.toString());
        if (Objects.isNull(cartDto.getConsumerId()) || Objects.isNull(cartDto.getProductType()) ||
                Objects.isNull(cartDto.getProductId()) || Objects.isNull(cartDto.getCount()) ||
                cartDto.getCount() < 0) {
            throw new IllegalArgumentException("Incorrect parameter");
        }
        Cart cart = new Cart();
        cart.setConsumerId(cartDto.getConsumerId());
        cart.setProductType(cartDto.getProductType());
        cart.setProductId(cartDto.getProductId());
        cart.setCount(cartDto.getCount());
        return cartRepository.save(cart);
    }

    public Cart updateCart(UUID cartId, ProductCountDto productCountDto) throws IllegalArgumentException, NotFoundException {
        if (Objects.isNull(productCountDto.getCount()) || productCountDto.getCount() < 0 || !Objects.nonNull(cartId)) {
            throw new IllegalArgumentException();
        }
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new NotFoundException(String.format("Cart with id %s is not found", cartId)));
        cart.setCount(productCountDto.getCount());
        return cartRepository.save(cart);
    }

    public void deleteCart(UUID cartId) throws NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new NotFoundException(String.format("Cart with id %s is not found", cartId)));
        cartRepository.delete(cart);
    }
}
