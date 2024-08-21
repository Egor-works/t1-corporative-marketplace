package ru.corpmarket.authservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCredentials {

    private String email;
    private String password;
    private UserRole userRole;
}
