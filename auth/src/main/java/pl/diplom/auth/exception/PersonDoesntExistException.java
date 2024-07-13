package pl.diplom.auth.exception;

public class PersonDoesntExistException extends RuntimeException{
    public PersonDoesntExistException(String message) {
        super(message);
    }
}
