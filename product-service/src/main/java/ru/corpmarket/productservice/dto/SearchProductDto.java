package ru.corpmarket.productservice.dto;

import lombok.*;
import ru.corpmarket.productservice.model.Size;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchProductDto {

    private List<String> colors;
    private List<Size> sizes;
    private Long price;

    private List<String> brands;
    private String title;
    private List<String> categories;
}