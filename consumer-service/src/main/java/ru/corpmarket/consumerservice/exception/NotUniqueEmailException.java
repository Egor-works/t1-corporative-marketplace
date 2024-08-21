package ru.corpmarket.consumerservice.exception;

public class NotUniqueEmailException extends Exception{
    public NotUniqueEmailException(String message) {
        super(message);
    }

    public NotUniqueEmailException() {
        super();
    }
}
