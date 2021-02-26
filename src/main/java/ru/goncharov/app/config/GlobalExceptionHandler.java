package ru.goncharov.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

/**
 * Global exception handler
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongCurrencyCodeException.class)
    protected ResponseEntity<?> handleWrongCurrencyCodeException(WrongCurrencyCodeException exception) {
        String errorCode = generatedLogCode();
        log.error("Error code = [ " + errorCode + " ] message:" + exception.getMessage());
        String errorMessage;
        errorMessage = "Error Code = [" + errorCode + "] message: " + exception.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleAllExceptions(RuntimeException exception) {
        String errorCode = generatedLogCode();
        log.error("Error code = [ " + errorCode + " ] message:" + exception.getMessage());
        String errorMessage = "Error Code = [" + errorCode + "] message: внутреняя ошибка сервера, попробуйте позднее.";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generatedLogCode() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }
}
