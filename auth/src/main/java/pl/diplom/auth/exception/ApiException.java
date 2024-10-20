package pl.diplom.auth.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException extends Exception{
    private final String message;
    private final String exceptionType;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.exceptionType = throwable.getClass().getName();
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }



    public String getMessage() {
        return message;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}