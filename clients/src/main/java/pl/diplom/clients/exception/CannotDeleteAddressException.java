package pl.diplom.clients.exception;

public class CannotDeleteAddressException extends RuntimeException {
    public CannotDeleteAddressException(String message) {
        super(message);
    }
}
