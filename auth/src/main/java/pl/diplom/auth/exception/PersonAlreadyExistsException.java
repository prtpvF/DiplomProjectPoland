package pl.diplom.auth.exception;

public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}
