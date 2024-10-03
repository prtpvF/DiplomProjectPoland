package com.example.demo.controller;

import com.example.demo.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/pizza")
public class PizzaController {

        private final PizzaService pizzaService;

        @GetMapping("/pizza")
        public void findPizzaByName(@RequestBody String pizzaName) {
            pizzaService.findPizza(pizzaName);
        }
}
