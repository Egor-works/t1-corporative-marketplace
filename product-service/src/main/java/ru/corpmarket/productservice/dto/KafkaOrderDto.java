package ru.corpmarket.productservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaOrderDto {

    private UUID productId;
    private Integer count;
}
