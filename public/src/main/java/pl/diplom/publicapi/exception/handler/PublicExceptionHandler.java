package pl.diplom.publicapi.exception.handler;

import pl.diplom.publicapi.exception.PersonNotFoundException;
import pl.diplom.publicapi.exception.PizzaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PublicExceptionHandler {

        @ExceptionHandler(value = PersonNotFoundException.class)
        public ResponseEntity personNotFpundExceptionHandler(PersonNotFoundException ex){
           HttpStatus status = HttpStatus.NOT_FOUND;
           return new ResponseEntity(ex.getMessage(), status);
        }

    @ExceptionHandler(value = PizzaNotFoundException.class)
    public ResponseEntity pizzaNotFoundExceptionHandler(PizzaNotFoundException ex){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity(ex.getMessage(), status);
    }
}
