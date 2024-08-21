package ru.corpmarket.productservice.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "clothes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
public class Product {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name")
    @ApiModelProperty(example = "Icon")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(example = "Some_text")
    private String description;

    @Column(name = "color")
    @ApiModelProperty(example = "Red")
    private String color;

    @Column(name = "size")
    @ApiModelProperty(example = "ONE_SIZE", notes = "From XXS to XXXXL")
    private Size size;

    @Column(name = "count")
    @ApiModelProperty(example = "54")
    private Integer count;

    @Column(name = "price")
    @ApiModelProperty(example = "999")
    private Integer price;

    @Column(name = "category")
    @ApiModelProperty(example = "Merch")
    private String category;

    @Column(name = "brand")
    @ApiModelProperty(example = "T1")
    private String brand;

}
