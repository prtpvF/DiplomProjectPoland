package pl.diplom.admin.exception;

public class RecipeAlreadyExistsException extends RuntimeException {

        public RecipeAlreadyExistsException(String message) {
            super(message);
        }
}
