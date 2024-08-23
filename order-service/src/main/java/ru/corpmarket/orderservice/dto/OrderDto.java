package ru.corpmarket.orderservice.dto;

import lombok.*;
import ru.corpmarket.orderservice.model.ProductType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    private UUID productId;

    private Integer count;

    private UUID consumerId;

    private ProductType productType;

    private Integer price;

    private String title;

}
