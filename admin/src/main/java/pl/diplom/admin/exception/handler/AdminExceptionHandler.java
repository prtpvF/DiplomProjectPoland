package pl.diplom.admin.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.admin.exception.PersonNotFoundException;

@ControllerAdvice
public class AdminExceptionHandler {

        @ExceptionHandler(value = { PersonNotFoundException.class })
        public ResponseEntity personNotFoundExceptionHandler(PersonNotFoundException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }
}
