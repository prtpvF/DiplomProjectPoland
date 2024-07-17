package com.example.demo.exception.handler;

import com.example.demo.exception.PizzaNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(value = PizzaNotFoundException.class)
    public ResponseEntity pizzaNotFoundExceptionHandler(PizzaNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.warn("pizza not found");
        return new ResponseEntity(exception, status);
    }
}
