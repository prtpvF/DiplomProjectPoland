package pl.diplom.admin.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.admin.exception.IllegalOrderStatusException;
import pl.diplom.admin.exception.IngredientNotFoundException;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.admin.exception.RecipeNotFoundException;

@ControllerAdvice
public class AdminExceptionHandler {

        @ExceptionHandler(value = { PersonNotFoundException.class })
        public ResponseEntity personNotFoundExceptionHandler(PersonNotFoundException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }

        @ExceptionHandler(value = { RecipeNotFoundException.class })
        public ResponseEntity recipeNotFoundExceptionHandler(RecipeNotFoundException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }

        @ExceptionHandler(value = { IngredientNotFoundException.class })
        public ResponseEntity ingredientNotFoundExceptionHandler(IngredientNotFoundException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }

        @ExceptionHandler(value = { IllegalOrderStatusException.class })
        public ResponseEntity illegalOrderStatusExceptionHandler(IllegalOrderStatusException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }
}
