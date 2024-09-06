package pl.diplom.producer.exception;

public class OrderStatusNotFoundException extends RuntimeException {

        public OrderStatusNotFoundException(String message) {
            super(message);
        }
}
