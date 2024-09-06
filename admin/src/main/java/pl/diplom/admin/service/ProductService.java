package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.DrinkDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.dto.SnackDto;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Portion;
import pl.diplom.common.model.enums.PizzaCreatorEnum;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.PortionRepository;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final IngredientService ingredientService;
        private final PortionService portionService;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;

        private final ImageService imageService;

        private final ModelMapper modelMapper;

        public HttpStatus createDrink(DrinkDto drinkDto,
                                      MultipartFile file) {
                Drink drink = new Drink();
                modelMapper.map(drinkDto, drink);
                drink.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                drink.setPathToImage(filePath);
                drinkRepository.save(drink);
                return CREATED;
        }

        @Transactional
        public HttpStatus createPizza(PizzaDto pizzaDto,
                                      MultipartFile file) {
                Pizza pizza = new Pizza();
                modelMapper.map(pizzaDto, pizza);
                List<Portion> portions = portionService
                        .findAllOrCreate(
                                pizzaDto.getPortions());
                pizza.setPortions(portions);
                pizza.setStatus(PizzaCreatorEnum.ADMIN.name());
                String filePath = imageService.savePhotoLocal(file);
                pizza.setPathToImage(filePath);
                pizzaRepository.save(pizza);
                return CREATED;
        }

        public HttpStatus createSnack(SnackDto snackDto,
                                      MultipartFile file) {
                Snack snack = new Snack();
                modelMapper.map(snackDto, snack);
                snack.setPersonOrderList(Collections.emptyList());
                String filePath = imageService.savePhotoLocal(file);
                snack.setPathToImage(filePath);
                snackRepository.save(snack);
                return CREATED;
        }

        public HttpStatus updatePizza(Integer pizzaNeedToBeUpdatedId,
                                      PizzaDto pizzaDto,
                                      MultipartFile file) {

                Pizza pizza = getPizzaById(pizzaNeedToBeUpdatedId);
                pizza.setId(pizza.getId());
                pizza.setPortions(
                        portionService.findAllOrCreate(
                                pizzaDto.getPortions()
                        )
                );
                pizza.setCost(pizzaDto.getCost());
                pizza.setName(pizzaDto.getName());
                if (!pizza.getPersonOrders().isEmpty()) {
                        pizza.setPersonOrders(pizza.getPersonOrders());
                }
                pizza.setDescription(pizzaDto.getDescription());
                if (file != null) {
                        String filePath = imageService.savePhotoLocal(file);
                        pizza.setPathToImage(filePath);
                }
                else {
                        pizza.setPathToImage(pizza.getPathToImage());
                }
                pizzaRepository.save(pizza);
                return OK;
        }

        public HttpStatus updateDrink(Integer drinkNeedToBeUpdatedId,
                                      DrinkDto drinkDto,
                                      MultipartFile file) {

                Drink drink = getDrinkById(drinkNeedToBeUpdatedId);
                drink.setId(drink.getId());
                if(!drink.getPersonOrderList().isEmpty()) {
                        drink.setPersonOrderList(drink.getPersonOrderList());
                }

                mapAdditionalFieldFoDrink(drinkDto, drink);

                if (file!=null) {
                        drink.setPathToImage(imageService.savePhotoLocal(file));
                }

                if(!drink.getPersonOrderList().isEmpty()) {
                        drink.setPersonOrderList(drink.getPersonOrderList());
                }
                drinkRepository.save(drink);
                return OK;
        }

        public HttpStatus updateSnack(Integer snackId,
                                      SnackDto snackDto,
                                      MultipartFile file) {

                Snack snack = getSnackById(snackId);

                snack.setId(snackId);

                if(!snack.getPersonOrderList().isEmpty()) {
                        snack.setPersonOrderList(snack.getPersonOrderList());
                }

                if(file!=null) {
                        snack.setPathToImage(imageService.savePhotoLocal(file));
                }

                mapAdditionalFieldForSnack(snackDto, snack);
                snackRepository.save(snack);
                return OK;
        }

        public HttpStatus deleteDrink(int drinkId) {
                Drink drink = getDrinkById(drinkId);
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
                snack.setDescription(snack.getDescription());
                snack.setCost(snackDto.getCost());
                snack.setName(snackDto.getName());
                snack.setWeight(snackDto.getWeight());
        }

        private void mapAdditionalFieldFoDrink(DrinkDto drinkDto, Drink drink) {
                drink.setTaste(drinkDto.getTaste());
                drink.setDescription(drinkDto.getDescription());
                drink.setCost(drinkDto.getCost());
                drink.setName(drinkDto.getName());
                drink.setTaste(drinkDto.getTaste());
        }
}