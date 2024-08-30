package ru.corpmarket.adminservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminDto {

    private String email;

    private String password;

    private String phoneNumber;

    private String name;

    private Long coinsId;
}
