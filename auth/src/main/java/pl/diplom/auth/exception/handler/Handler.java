package pl.diplom.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.auth.exception.ApiException;
import pl.diplom.auth.exception.PersonAlreadyExistsException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<Object> personAlreadyExistsExceptionHandler(PersonAlreadyExistsException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(e.getMessage(), e, status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, status);
    }
}
