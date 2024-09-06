package pl.diplom.clients.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.clients.exception.*;

@ControllerAdvice
public class PersonExceptionHandler {

        @ExceptionHandler(PersonNotFoundException.class)
        public ResponseEntity handlePersonNotFoundException(PersonNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(PizzaNotFoundException.class)
        public ResponseEntity pizzaNotFoundExceptionHandler(PizzaNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(PersonOrderNotFoundException.class)
        public ResponseEntity personOrderNotFoundExceptionHandler(PersonOrderNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(AddressAlreadyExistsException.class)
        public ResponseEntity addressAlreadyExistsExceptionHandler(AddressAlreadyExistsException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(DrinkNotFoundException.class)
        public ResponseEntity drinkNotFoundExceptionHandler(DrinkNotFoundException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(SnackNotFoundException.class)
        public ResponseEntity snackNotFoundExceptionHandler(SnackNotFoundException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(CannotDeleteOrderException.class)
        public ResponseEntity cannotDeleteOrderExceptionHandler(CannotDeleteOrderException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(IllegalOrderOwnerException.class)
        public ResponseEntity illegalOrderOwnerExceptionExceptionHandler(IllegalOrderOwnerException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }
}
