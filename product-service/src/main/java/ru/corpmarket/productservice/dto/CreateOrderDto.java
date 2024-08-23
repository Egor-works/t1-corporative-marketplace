package ru.corpmarket.productservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderDto {

    private UUID productId;
    private Integer count;
}
