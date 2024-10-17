package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.*;
import pl.diplom.common.model.Image;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PizzaCreatorEnum;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;
        private final PersonOrderRepository personOrderRepository;
        private final IngredientRepository inventoryRepository;
        private final ImageService imageService;

        private final ModelMapper modelMapper;
        private final IngredientRepository ingredientRepository;

        public Page<PizzaDtoResponse> getPizzaPage(Pageable pageable) {
                Page<Pizza> pizzaPage = pizzaRepository.findAll(pageable);
                Page<PizzaDtoResponse> dtoPage = pizzaPage.map(pizza -> getPizzaDto(pizza));
                return dtoPage;
        }

        @Transactional
        public HttpStatus createDrink(DrinkDto drinkDto,
                                      MultipartFile file) throws IOException {
                Drink drink = new Drink();
                modelMapper.map(drinkDto, drink);
                Image image;

                        image = toImageEntity(file);
                        image.setPreviewImage(true);
                        drink.addImage(image);

                drink.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                drink.setPathToImage(filePath);
                drinkRepository.save(drink);
                return CREATED;
        }

        @Transactional
        public HttpStatus createPizza(PizzaDto pizzaDto,
                                      MultipartFile file) throws IOException {
                Pizza pizza = mapAdditionalFields(pizzaDto);
                pizza.setIngredients(
                        ingredientRepository.findAllById(
                                pizzaDto.getIngredients()
                        )
                );
                pizza.setStatus(PizzaCreatorEnum.ADMIN.name());
                String filePath = imageService.savePhotoLocal(file);
                pizza.setPathToImage(filePath);
                List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDto.getIngredients());
                pizza.setIngredients(ingredients);
                for(Ingredient ingredient : pizza.getIngredients()) {
                        ingredient.getPizza().add(pizza);
                }
                pizzaRepository.save(pizza);
                ingredientRepository.saveAll(ingredients);
                return CREATED;
        }

        @Transactional
        public HttpStatus createSnack(SnackDto snackDto,
                                      MultipartFile file) throws IOException {
                Snack snack = new Snack();
                modelMapper.map(snackDto, snack);
                snack.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                snack.setPathToImage(filePath);
                snackRepository.save(snack);
                return CREATED;
        }

        public HttpStatus updatePizza(Integer pizzaNeedToBeUpdatedId, PizzaDto pizzaDto) {
                Pizza pizza = getPizzaById(pizzaNeedToBeUpdatedId);
                List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDto.getIngredients());
                pizza.setIngredients(ingredients);
                for (Ingredient ingredient : ingredients) {
                        ingredient.getPizza().remove(pizza);
                }
                ingredientRepository.saveAll(ingredients);
                pizza.setCost(pizzaDto.getCost());
                pizza.setName(pizzaDto.getName());
                if (!pizza.getPersonOrders().isEmpty()) {
                        pizza.setPersonOrders(pizza.getPersonOrders());
                } else {
                        pizza.setPathToImage(pizza.getPathToImage());
                }
                pizza.setIngredients(Collections.emptyList());
                Pizza pizza1  = pizzaRepository.save(pizza);
                for (Ingredient ingredient : ingredients) {
                        ingredient.getPizza().add(pizza1);
                }
                pizza1.setIngredients(ingredients);
                pizzaRepository.save(pizza1);
                ingredientRepository.saveAll(ingredients);

                return HttpStatus.OK;
        }

        public HttpStatus updateDrink(Integer drinkNeedToBeUpdatedId,
                                      DrinkDto drinkDto) {

                Drink drinkNeed = getDrinkById(drinkNeedToBeUpdatedId);
                drinkNeed.setId(drinkNeedToBeUpdatedId);
                if(!drinkNeed.getPersonOrderList().isEmpty()) {
                        drinkNeed.setPersonOrderList(drinkNeed.getPersonOrderList());
                }
                drinkNeed.setPathToImage(drinkNeed.getPathToImage());
                drinkNeed.setCost(drinkDto.getCost());
                drinkNeed.setName(drinkDto.getName());
                drinkNeed.setTaste(drinkDto.getTaste());
                drinkNeed.setVolume(drinkDto.getVolume());
                drinkRepository.save(drinkNeed);
                return OK;
        }

        public HttpStatus updateSnack(Integer snackId,
                                      SnackDto snackDto) {

                Snack snack = getSnackById(snackId);

                snack.setId(snackId);

                if(!snack.getPersonOrderList().isEmpty()) {
                        snack.setPersonOrderList(snack.getPersonOrderList());
                }
                snack.setPathToImage(snack.getPathToImage());
                snack.setCost(snackDto.getCost());
                snack.setName(snackDto.getName());
                snack.setWeight(snackDto.getWeight());
                snackRepository.save(snack);
                return OK;
        }

        public HttpStatus deleteDrink(int drinkId) {
                Drink drink = getDrinkById(drinkId);
                for (PersonOrder order : drink.getPersonOrderList()) {
                        order.getDrinks().remove(drink);
                }
                personOrderRepository.saveAll(drink.getPersonOrderList());
                drinkRepository.delete(drink);
                return OK;
        }

        public HttpStatus deletePizza(int pizzaId) {
                Pizza pizza = getPizzaById(pizzaId);
                pizzaRepository.delete(pizza);
                return OK;
        }

        public HttpStatus deleteSnack(int snackId) {
                Snack snack = getSnackById(snackId);
                snackRepository.delete(snack);
                return OK;
        }

        private Drink getDrinkById(Integer drinkNeedToBeUpdatedId) {
                return drinkRepository.findById(drinkNeedToBeUpdatedId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find drink with this id"));
        }

        private Pizza getPizzaById(Integer pizzaNeedToBeUpdatedId) {
                return pizzaRepository.findById(pizzaNeedToBeUpdatedId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find pizza with this id"));
        }

        private Snack getSnackById(Integer snackId) {
                return snackRepository.findById(snackId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find snack with this id"
                        ));
        }

        private void mapAdditionalFieldForSnack(SnackDto snackDto, Snack snack) {
                snack.setCost(snackDto.getCost());
                snack.setName(snackDto.getName());
                snack.setWeight(snackDto.getWeight());
        }

        private void mapAdditionalFieldFoDrink(DrinkDto drinkDto, Drink drink) {
                drink.setTaste(drinkDto.getTaste());
                drink.setCost(drinkDto.getCost());
                drink.setName(drinkDto.getName());
                drink.setTaste(drinkDto.getTaste());
        }

        private Pizza mapAdditionalFields(PizzaDto pizzaDto) {
                Pizza pizza = new Pizza();
                pizza.setName(pizzaDto.getName());
                pizza.setCost(pizzaDto.getCost());
                return pizza;
        }

        private static Image toImageEntity(MultipartFile file1) throws IOException { //конвертирование фотографии в сущность
                Image image = new Image();
                image.setName(file1.getName());
                image.setOriginalFilename(file1.getOriginalFilename());
                image.setContentType(file1.getContentType());
                image.setSize(file1.getSize());
                image.setBytes(file1.getBytes());
                return image;
        }

        private PizzaDtoResponse getPizzaDto(Pizza pizza) {
                PizzaDtoResponse pizzaDto = new PizzaDtoResponse();
                pizzaDto.setId(pizza.getId());
                pizzaDto.setName(pizza.getName());
                pizzaDto.setCost(pizza.getCost());
                pizzaDto.setPathToImage(pizza.getPathToImage());
                List<IngredientDto> ingredientDtos = new ArrayList<>();
                for (Ingredient portion : pizza.getIngredients()) {
                        ingredientDtos.add(convertAnyIngredientToDto(portion));
                }
                pizzaDto.setIngredients(ingredientDtos);
                return pizzaDto;
        }

        private IngredientDto convertAnyIngredientToDto(Ingredient ingredient) {
                IngredientDto ingredientDto = new IngredientDto();
                ingredientDto.setId(ingredient.getId());
                ingredientDto.setName(ingredient.getName());
                ingredientDto.setCost(ingredient.getCost());
                ingredientDto.setWeight(ingredient.getWeight());
                return ingredientDto;
        }
}