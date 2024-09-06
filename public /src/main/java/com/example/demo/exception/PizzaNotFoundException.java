package com.example.demo.exception;

public class PizzaNotFoundException extends RuntimeException {

        public PizzaNotFoundException(String message) {
            super(message);
        }
}
