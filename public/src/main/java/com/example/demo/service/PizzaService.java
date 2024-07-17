package com.example.demo.service;

import com.example.demo.exception.PizzaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Pizza;
import pl.diplom.common.repository.PizzaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    public List<Pizza> allPizza(){
        return pizzaRepository.findAll();
    }

    public Pizza findPizza(int id){
        return pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException("pizza with this id not found"));
    }


    public Pizza findByName(String name) {
        return pizzaRepository.findPizzaByName(name)
                .orElseThrow(() -> new PizzaNotFoundException("pizza not found"));
    }

}
