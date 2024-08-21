package ru.corpmarket.authservice.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.corpmarket.authservice.model.UserCredentials;

@Slf4j
@NoArgsConstructor
public class UserService {

    private UserCredentials userCredentials;
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}
