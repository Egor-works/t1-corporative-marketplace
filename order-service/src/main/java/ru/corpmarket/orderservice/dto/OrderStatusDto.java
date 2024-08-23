package ru.corpmarket.orderservice.dto;

import lombok.*;
import ru.corpmarket.orderservice.model.Status;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderStatusDto {
    private Status status;
}
