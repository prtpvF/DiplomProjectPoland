package com.example.demo.service;

import com.example.demo.dto.PizzaDto;
import com.example.demo.exception.PizzaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.repository.product.PizzaRepository;

@Service
@RequiredArgsConstructor
public class PizzaService {

        private final PizzaRepository pizzaRepository;

        public PizzaDto findPizza(String pizzaName) {
            return findPizzaDtoByName(pizzaName);
        }

        private PizzaDto findPizzaDtoByName(String name) {
            Pizza pizza = pizzaRepository.findPizzaByName(name)
                    .orElseThrow(() -> new PizzaNotFoundException
                            ("cannot find pizza with this name"));
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setName(pizza.getName());
            pizzaDto.setCost(pizza.getCost());
            pizzaDto.setPathToImage(pizza.getPathToImage());
            return pizzaDto;
        }
}
