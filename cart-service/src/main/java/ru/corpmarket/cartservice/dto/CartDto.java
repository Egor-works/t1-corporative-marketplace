package ru.corpmarket.cartservice.dto;

import ru.corpmarket.cartservice.model.ProductType;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto {

    private UUID consumerId;
    private ProductType productType;
    private UUID productId;
    private Integer count;
}
