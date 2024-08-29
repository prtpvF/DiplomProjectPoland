package pl.diplom.admin.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.diplom.admin.exception.*;

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

        @ExceptionHandler(value = { PersonRoleNotFoundException.class })
        public ResponseEntity personRoleNotFoundExceptionHandler(PersonRoleNotFoundException ex) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity(ex.getMessage(), status);
        }

        @ExceptionHandler(value = { IllegalPersonDataException.class })
        public ResponseEntity illegalPersonDataExceptionHandler(IllegalPersonDataException ex) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(ex.getMessage(), status);
        }

        @ExceptionHandler(value = { IllegalArgumentException.class })
        public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException ex) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(ex.getMessage(), status);
        }
}
