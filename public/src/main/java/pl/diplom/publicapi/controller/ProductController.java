package pl.diplom.publicapi.controller;

import org.springframework.web.bind.annotation.*;
import pl.diplom.publicapi.dto.DrinkDto;
import pl.diplom.publicapi.dto.PizzaDto;
import pl.diplom.publicapi.dto.ProductDto;
import pl.diplom.publicapi.dto.SnackDto;
import pl.diplom.publicapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
    @RequiredArgsConstructor
    @RequestMapping("/public/product")
    @Slf4j
    public class ProductController {

        private final ProductService productService;

        @GetMapping("/popular")
        public List<ProductDto> getPopularProducts() {
            return productService.getPopularProducts();
        }

        @GetMapping("/pizza")
        public Page<PizzaDto> getPizzaPage(Pageable pageable) {
            return productService.getPizzaPage(pageable);
        }

        @GetMapping("/drinks")
        public Page<DrinkDto> getDrinkPage(Pageable pageable) {
            return productService.getDrinkPage(pageable);
        }

        @GetMapping("/snacks")
        public Page<SnackDto> getSnackPage(Pageable pageable) {
            return productService.getSnackPage(pageable);
        }

        @GetMapping("/findByName")
        public List<ProductDto> findProductByName(@RequestParam String name) {
            return productService.findProductsByName(name);
        }


        @GetMapping("/pizza/{id}")
        public PizzaDto getPizzaById(@PathVariable("id") Integer id) {
            return productService.getPizza(id);
        }

        @GetMapping("/drink/{id}")
        public DrinkDto getDrinkById(@PathVariable("id") Integer id) {
            return productService.getDrink(id);
        }

        @GetMapping("/snack/{id}")
        public SnackDto getSnackById(@PathVariable("id") Integer id) {
            return productService.getSnack(id);
        }

}
