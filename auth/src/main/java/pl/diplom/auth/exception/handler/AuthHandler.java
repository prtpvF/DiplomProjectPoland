package pl.diplom.auth.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.auth.exception.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class AuthHandler {

        @ExceptionHandler(PersonAlreadyExistsException.class)
        public ResponseEntity<Object> personAlreadyExistsExceptionHandler(PersonAlreadyExistsException e){
            HttpStatus status = BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
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
            HttpStatus status = BAD_REQUEST;
            ApiException exception = new ApiException(e.getMessage(),e, status, ZonedDateTime.now(ZoneId.of("Z")));
            return new ResponseEntity<>(exception,status);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException e){
            HttpStatus status =  BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }

        @ExceptionHandler(IllegalAgeException.class)
        public ResponseEntity illegalAgeExceptionHandler(IllegalAgeException e){
            HttpStatus status =  BAD_REQUEST;
            return new ResponseEntity(e.getMessage(), status);
        }
}
