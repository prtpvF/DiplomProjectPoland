package com.example.demo.service;

import com.example.demo.dto.DrinkDto;
import com.example.demo.dto.PizzaDto;
import com.example.demo.dto.SnackDto;
import com.example.demo.exception.PizzaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;

        public Page<PizzaDto> getPizzaPage(Pageable pageable) {
            Page<Pizza> pizzaPage = pizzaRepository.findAll(pageable);
            Page<PizzaDto> dtoPage = pizzaPage.map(pizza -> getPizzaDto(pizza));
            return dtoPage;
        }

        public Page<DrinkDto> getDrinkPage(Pageable pageable) {
            Page<Drink> drinkPage = drinkRepository.findAll(pageable);
            Page<DrinkDto> dtoPage = drinkPage.map(drink -> getDrinkDto(drink));
            return dtoPage;
        }

        public Page<SnackDto> getSnackPage(Pageable pageable) {
            Page<Snack> snackPage = snackRepository.findAll(pageable);
            Page<SnackDto> dtoPage = snackPage.map(snack -> getSnackDto(snack));
            return dtoPage;
        }

        public PizzaDto getPizza(Integer pizzaId) {
            PizzaDto pizzaDto = new PizzaDto();
            Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(
                    () -> new PizzaNotFoundException("cannot find pizza")
            );
            pizzaDto.setName(pizza.getName());
            pizzaDto.setId(pizza.getId());
            pizzaDto.setCost(pizza.getCost());
            pizzaDto.setPathToImage(pizza.getPathToImage());
            return pizzaDto;
        }

        public DrinkDto getDrink(Integer drinkId) {
            Drink drink = drinkRepository.findById(drinkId).orElseThrow(
                    () -> new PizzaNotFoundException("cannot find drink")
            );
            DrinkDto drinkDto = new DrinkDto();
            drinkDto.setId(drink.getId());
            drinkDto.setName(drink.getName());
            drinkDto.setCost(drink.getCost());
            drinkDto.setVolume(drink.getVolume());
            drinkDto.setPathToImage(drink.getPathToImage());
            drinkDto.setTaste(drink.getTaste());
            return drinkDto;
        }

        public SnackDto getSnack(Integer snackId) {
            Snack snack = snackRepository.findById(snackId).orElseThrow(
                    () -> new PizzaNotFoundException("cannot find snack")
            );
            SnackDto snackDto = new SnackDto();
            snackDto.setId(snack.getId());
            snackDto.setName(snack.getName());
            snackDto.setCost(snack.getCost());
            snackDto.setPathToImage(snack.getPathToImage());
            snackDto.setWeight(snack.getWeight());
            return snackDto;
        }

        private SnackDto getSnackDto(Snack snack) {
            SnackDto snackDto = new SnackDto();
            snackDto.setName(snack.getName());
            snackDto.setCost(snack.getCost());
            snackDto.setWeight(snack.getWeight());
            snackDto.setId(snack.getId());
            return snackDto;
        }

        private DrinkDto getDrinkDto(Drink drink) {
            DrinkDto dto = new DrinkDto();
            dto.setId(drink.getId());
            dto.setName(drink.getName());
            dto.setTaste(drink.getTaste());
            dto.setVolume(drink.getVolume());
            dto.setCost(drink.getCost());
            return dto;
        }

        private PizzaDto getPizzaDto(Pizza pizza) {
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizza.getId());
            pizzaDto.setName(pizza.getName());
            pizzaDto.setCost(pizza.getCost());
            pizzaDto.setPathToImage(pizza.getPathToImage());
            return pizzaDto;
        }
}
