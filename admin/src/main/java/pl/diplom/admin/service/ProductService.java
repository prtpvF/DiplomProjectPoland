package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.DrinkDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.dto.SnackDto;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final IngredientService ingredientService;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;

        private final ImageService imageService;

        private final ModelMapper modelMapper;

        public HttpStatus createDrink(DrinkDto drinkDto, MultipartFile file) {
                Drink drink = new Drink();
                modelMapper.map(drinkDto, drink);
                drink.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                drink.setPathToImage(filePath);
                drinkRepository.save(drink);
                return CREATED;
        }

        public HttpStatus createPizza(PizzaDto pizzaDto, MultipartFile file) {
                Pizza pizza = new Pizza();
                modelMapper.map(pizzaDto, pizza);
                List<Ingredient> ingredients = ingredientService.getAllIngredientsById(pizzaDto.getIngredientsIds());
                ingredients.forEach(ingredient -> ingredient.getPizza().add(pizza));
                pizza.setIngredients(ingredients);
                String filePath = imageService.savePhotoLocal(file);
                pizza.setPathToImage(filePath);
                pizzaRepository.save(pizza);
                return CREATED;
        }

        public HttpStatus createSnack(SnackDto snackDto,MultipartFile file) {
                Snack snack = new Snack();
                modelMapper.map(snackDto, snack);
                snack.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                snack.setPathToImage(filePath);
                snackRepository.save(snack);
                return CREATED;
        }
}
