package ru.corpmarket.productservice.dto;

import lombok.*;
import ru.corpmarket.productservice.model.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private String name;
    private String description;
    private String color;
    private Size size;
    private Integer count;
    private Integer price;
    private String category;
    private String brand;

}
