package com.example.demo.controller;

import com.example.demo.dto.DrinkDto;
import com.example.demo.dto.PizzaDto;
import com.example.demo.dto.SnackDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-product")
public class ProductController {

        private final ProductService productService;

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
