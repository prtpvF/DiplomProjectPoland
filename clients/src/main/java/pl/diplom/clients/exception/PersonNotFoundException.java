package pl.diplom.clients.exception;

public class PersonNotFoundException extends RuntimeException {

        public PersonNotFoundException(String message) {
            super(message);
        }
}
