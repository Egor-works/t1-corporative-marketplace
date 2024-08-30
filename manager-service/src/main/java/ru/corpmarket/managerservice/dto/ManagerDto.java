package ru.corpmarket.managerservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerDto {
    private String email;

    private String password;

    private String phoneNumber;

    private String name;

    private Long coinsId;
}
