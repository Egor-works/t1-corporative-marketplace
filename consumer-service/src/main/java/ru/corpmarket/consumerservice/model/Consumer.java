package ru.corpmarket.consumerservice.model;

import io.swagger.annotations.ApiModelProperty;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "consumers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Consumer {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @ApiModelProperty(example = "123")
    private UUID id;

    @Column(name = "email")
    @ApiModelProperty(example = "ivan@company.com", position = 1)
    private String email;

    @Column(name = "password")
    @ApiModelProperty(notes = "In requests decoded, in responses encoded", position = 2)
    private String password;

    @Column(name = "phone_number")
    @ApiModelProperty(example = "88005553535", position = 3)
    private Integer phoneNumber;

    @Column(name = "name")
    @ApiModelProperty(example = "Ivan Ivanov", position = 4)
    private String name;

    @Column(name = "coins")
    @ApiModelProperty(example = "0", position = 5)
    private Integer coins;
}
