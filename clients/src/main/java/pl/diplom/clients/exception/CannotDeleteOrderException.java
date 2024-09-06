package pl.diplom.clients.exception;

public class CannotDeleteOrderException extends RuntimeException {

        public CannotDeleteOrderException(String message) {
            super(message);
        }
}
