package pl.diplom.admin.controller.pizza;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.service.IngredientService;
import pl.diplom.admin.service.ProductService;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.product.PizzaRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class PizzaController {

    private final ProductService productService;

    @PostMapping(value = "/pizza/create")
    public HttpStatus createPizza(@ModelAttribute PizzaDto pizzaDto) {

        try {
            productService.createPizza(pizzaDto, pizzaDto.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return HttpStatus.CREATED;
    }

    @PatchMapping("/pizza/{id}")
    public HttpStatus updatePizza(@PathVariable("id") Integer pizzaId,
                                  @RequestBody PizzaDto pizzaDto) {
         productService.updatePizza(pizzaId, pizzaDto);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/pizza/{id}")
    public HttpStatus deletePizza(@PathVariable("id") Integer pizzaId) {
         productService.deletePizza(pizzaId);
            return HttpStatus.CREATED;
    }
}