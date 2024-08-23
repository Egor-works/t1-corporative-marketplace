package ru.corpmarket.orderservice.dto;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductServiceDto {
    private UUID productId;
    private Integer count;
}
