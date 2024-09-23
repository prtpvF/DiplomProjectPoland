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

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class PizzaController {

    private final ProductService productService;
    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @GetMapping("/update/pizza/{id}/page")
    public String updatePizzaPage(@PathVariable("id") int id,
                                  Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);
        return "/pizza/update";
    }

    @GetMapping("/pizza/{id}")
    public String getPizza(@PathVariable("id") Integer id, Model model) {
        setAuth(model);
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);
        return "/pizza/page";
    }

    @PostMapping(value = "/pizza/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String createPizza(@ModelAttribute @Valid PizzaDto pizzaDto,
                              @RequestPart("image") MultipartFile image,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "pizza/create";
        }

        try {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            productService.createPizza(pizzaDto, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/all/pizza"; // Перенаправление на успешную страницу
    }

    @GetMapping("/pizza/create")
    public String createPizzaPage(Model model) {
        setAuth(model);
        model.addAttribute("pizzaDto", new PizzaDto());

        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredientService.getAllIngredients());
        return "pizza/create";
    }


    @PatchMapping("/pizza/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus updatePizza(@PathVariable("id") Integer pizzaId,
                                  @RequestPart PizzaDto pizzaDto,
                                  @RequestPart MultipartFile image) {
        return productService.updatePizza(pizzaId, pizzaDto, image);
    }

    @DeleteMapping("/pizza/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus deletePizza(@PathVariable("id") Integer pizzaId) {
        return productService.deletePizza(pizzaId);
    }

    @GetMapping("/all/pizza")
    public String allPizza(Model model) {
        setAuth(model);

        model.addAttribute("pizza", pizzaRepository.findAll());
        return "/pizza/all";
    }

    private void setAuth(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("userRole", userRole);
    }
}
