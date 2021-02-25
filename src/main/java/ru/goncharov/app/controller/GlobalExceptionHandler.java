package ru.goncharov.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> handleAllExceptions(Throwable exception){
        String errorCode = generatedLogCode();
        log.error("Error code = [" + errorCode + " ] message:" + exception.getMessage());
        String errorMessage = "Error Code = [" + errorCode + "] message: внутреняя ошибка сервера, попробуйте позднее.";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generatedLogCode(){
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }
}
