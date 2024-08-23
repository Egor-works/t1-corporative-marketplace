package ru.corpmarket.consumerservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumerDto {

    private String email;

    private String password;

    private String phoneNumber;

    private String name;

    private Integer coins;
}
