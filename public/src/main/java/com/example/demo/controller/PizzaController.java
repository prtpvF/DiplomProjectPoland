package com.example.demo.controller;

import com.example.demo.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.diplom.common.model.Pizza;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/pizza")
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping("/{id}")
    public Pizza findPizza(@PathVariable("id") int id){
        return pizzaService.findPizza(id);
    }

    @GetMapping("/all")
    public List<Pizza> allPizza(){
        return pizzaService.allPizza();
    }

    @GetMapping("/{name}")
    public Pizza findPizzaByName(@PathVariable("name") String name){
        return pizzaService.findByName(name);
    }
}