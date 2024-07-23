package pl.diplom.auth.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.auth.exception.ApiException;
import pl.diplom.auth.exception.IncorrectPasswordException;
import pl.diplom.auth.exception.PersonAlreadyExistsException;
import pl.diplom.auth.exception.PersonDoesntExistException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AuthHandler {

        @ExceptionHandler(PersonAlreadyExistsException.class)
        public ResponseEntity<Object> personAlreadyExistsExceptionHandler(PersonAlreadyExistsException e){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ApiException exception = new ApiException(e.getMessage(), e, status, ZonedDateTime.now(ZoneId.of("Z")));
            return new ResponseEntity<>(exception, status);
        }

        @ExceptionHandler(PersonDoesntExistException.class)
        public ResponseEntity<Object> personDoesntExistExceptionHandler(PersonDoesntExistException e){
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiException exception = new ApiException(e.getMessage(),e, status, ZonedDateTime.now(ZoneId.of("Z")));
            return new ResponseEntity<>(exception,status);
        }

        @ExceptionHandler(JWTVerificationException.class)
        public ResponseEntity<Object> tokenHasExpiredHandler(JWTVerificationException e){
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            ApiException exception = new ApiException(e.getMessage(),e, status, ZonedDateTime.now(ZoneId.of("Z")));
            return new ResponseEntity<>(exception,status);
        }

        @ExceptionHandler(IncorrectPasswordException.class)
        public ResponseEntity<Object> incorrectPasswordExceptionHandler(IncorrectPasswordException e){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ApiException exception = new ApiException(e.getMessage(),e, status, ZonedDateTime.now(ZoneId.of("Z")));
            return new ResponseEntity<>(exception,status);
        }
}
