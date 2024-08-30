package ru.corpmarket.managerservice.exception;

public class NotUniqueEmailException extends Exception{
    public NotUniqueEmailException(String message) {
        super(message);
    }

    public NotUniqueEmailException() {
        super();
    }
}
