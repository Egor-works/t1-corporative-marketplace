package ru.corpmarket.productservice.exception;

public class RunOutProductException extends Exception{
    public RunOutProductException(String message) {
        super(message);
    }

    public RunOutProductException() {
        super();
    }
}
