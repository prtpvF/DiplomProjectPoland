package pl.diplom.clients.exception;

public class AddressAlreadyExistsException extends RuntimeException {

        public AddressAlreadyExistsException(String message) {
            super(message);
        }
}
