package ru.corpmarket.adminservice.exception;

public class NotUniqueEmailException extends Exception{
    public NotUniqueEmailException(String message) {
        super(message);
    }

    public NotUniqueEmailException() {
        super();
    }
}
