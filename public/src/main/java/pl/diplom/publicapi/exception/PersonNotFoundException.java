package pl.diplom.publicapi.exception;

public class PersonNotFoundException extends RuntimeException {

        public PersonNotFoundException(String message) {
            super(message);
        }
}
