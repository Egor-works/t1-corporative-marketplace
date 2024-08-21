package ru.corpmarket.consumerservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumerDto {

    @ApiModelProperty(example = "ivan@company.com")
    private String email;

    @ApiModelProperty(notes = "In requests decoded, in responses encoded")
    private String password;

    @ApiModelProperty(example = "88005553535")
    private Integer phoneNumber;

    @ApiModelProperty(example = "Ivan Ivanov")
    private String name;

    @ApiModelProperty(example = "0")
    private Integer coins;
}
