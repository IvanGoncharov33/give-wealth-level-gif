package ru.goncharov.app.config;

public class WrongCurrencyCodeException extends RuntimeException {
    public WrongCurrencyCodeException(String message) {
        super(message);
    }
}
