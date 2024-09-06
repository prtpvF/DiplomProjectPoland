package pl.diplom.clients.exception;

public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(String message) {
        super(message);
    }
}
