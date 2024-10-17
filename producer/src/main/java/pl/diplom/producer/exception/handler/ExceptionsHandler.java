package pl.diplom.producer.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.producer.exception.OrderStatusNotFoundException;
import pl.diplom.producer.exception.PersonNotFoundException;
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

        @ExceptionHandler(value = EntityNotFoundException.class)
        public ResponseEntity entityNotFoundExceptionHandler(EntityNotFoundException ex){
            HttpStatus status = HttpStatus.NOT_FOUND;
            log.info("person order not found");
            return new ResponseEntity(ex, status);
        }

        @ExceptionHandler(value = OrderStatusNotFoundException.class)
        public ResponseEntity orderStatusNotFoundExceptionHandler(OrderStatusNotFoundException ex){
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex, status);
        }

        @ExceptionHandler(value = PersonNotFoundException.class)
        public ResponseEntity personNotFoundExceptionHandler(PersonNotFoundException ex){
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex, status);
        }

        @ExceptionHandler(value = AccessDeniedException.class)
        public ResponseEntity accessDeniedExceptionHandler(AccessDeniedException ex){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(ex.getMessage(), status);
        }
}
