package pl.diplom.publicapi.service;

import pl.diplom.common.model.product.Product;
import pl.diplom.publicapi.dto.DrinkDto;
import pl.diplom.publicapi.dto.PizzaDto;
import pl.diplom.publicapi.dto.ProductDto;
import pl.diplom.publicapi.dto.SnackDto;
import pl.diplom.publicapi.exception.PizzaNotFoundException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;

        public List<ProductDto> findProductsByName(String name) {
            List<Pizza> pizzas = pizzaRepository.findByName(name);
            List<Drink> drinks = drinkRepository.findByName(name);
            List<Snack> snacks = snackRepository.findByName(name);
            List<ProductDto> products = new ArrayList<>();

            for (Pizza pizza : pizzas) {
                products.add(convertAnyProductToDto(pizza, pizza.getId()));
            }

            for (Drink drink : drinks) {
                products.add(convertAnyProductToDto(drink, drink.getId()));
            }

            for (Snack snack : snacks) {
                products.add(convertAnyProductToDto(snack, snack.getId()));
            }
            return products;
        }

        public List<ProductDto> getPopularProducts() {
            int countOfPizza = pizzaRepository.findAll().size();
            int countOfDrinks = drinkRepository.findAll().size();
            int countOfSnacks = snackRepository.findAll().size();

            List<ProductDto> productDtoList = new ArrayList<>();

            int randomPizzaId = getRandomProductId(countOfPizza);
            int randomDrinkId = getRandomProductId(countOfDrinks);
            int randomSnackId = getRandomProductId(countOfSnacks);

            productDtoList.add(
                    convertAnyProductToDto(drinkRepository.findById(randomDrinkId).get(), randomDrinkId)
            );

            productDtoList.add(
                    convertAnyProductToDto(pizzaRepository.findById(randomPizzaId).get(), randomPizzaId)
            );

            productDtoList.add(
                    convertAnyProductToDto(snackRepository.findById(randomSnackId).get(), randomSnackId)
            );

            return productDtoList;
        }

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
            snackDto.setPathToImage(snack.getPathToImage());
            return snackDto;
        }

        private DrinkDto getDrinkDto(Drink drink) {
            DrinkDto dto = new DrinkDto();
            dto.setId(drink.getId());
            dto.setName(drink.getName());
            dto.setTaste(drink.getTaste());
            dto.setVolume(drink.getVolume());
            dto.setCost(drink.getCost());
            dto.setPathToImage(drink.getPathToImage());
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

        private ProductDto convertAnyProductToDto(Product product, int id) {
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            productDto.setName(product.getName());
            productDto.setCost(product.getCost());
            productDto.setPathToImage(productDto.getPathToImage());
            return productDto;
        }

        private int getRandomProductId(int size) {

            if(size == 1) {
                return 1;
            }
            Random random = new Random();
            return random.nextInt(1, size+1);
        }
}
