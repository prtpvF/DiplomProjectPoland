package pl.diplom.producer.exception;

public class PersonNotFoundException extends RuntimeException {

        public PersonNotFoundException(String message) {
            super(message);
        }
}
