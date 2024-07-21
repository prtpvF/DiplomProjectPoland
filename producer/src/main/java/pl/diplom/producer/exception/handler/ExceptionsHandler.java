package pl.diplom.producer.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.producer.exception.ProductNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity productNotFoundExceptionHandler(ProductNotFoundException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.info("product not found");
        return new ResponseEntity(ex, status);
    }
}
