package pl.diplom.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;

        @PostMapping("/ingredient")
        public ResponseEntity createIngredient(@Valid @RequestBody IngredientDto ingredientDto,
                                               BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity("both fields must be filled",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("Ingredient created",
                    adminService.createIngredient(ingredientDto));
        }

        @PostMapping("/pizza")
        public HttpStatus createPizza(@RequestHeader("token") String token,
                @RequestBody PizzaDto pizzaDto) {
            adminService.createPizza(pizzaDto);
            return HttpStatus.CREATED;
        }
}
