package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.DrinkDto;
import pl.diplom.clients.dto.PizzaDto;
import pl.diplom.clients.dto.PortionDto;
import pl.diplom.clients.dto.SnackDto;
import pl.diplom.clients.exception.DrinkNotFoundException;
import pl.diplom.clients.exception.PizzaNotFoundException;
import pl.diplom.clients.exception.SnackNotFoundException;
import pl.diplom.common.model.Portion;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Product;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final PizzaRepository pizzaRepository;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;
        private final NotificationService notificationService;

        public List<Pizza> getConvertedPizzaList(List<Integer> pizzaIdList) {
            List<Pizza> convertedPizzaList = new ArrayList<>();

            for (Integer pizzaId : pizzaIdList) {
                convertedPizzaList.add(pizzaRepository
                        .findById(pizzaId)
                        .orElseThrow(() ->
                                new PizzaNotFoundException("Pizza not found. Try again")));
            }
            return convertedPizzaList;
        }

        public List<Drink> getConvertedDrinkList(List<Integer> drinkIdList) {
            List<Drink> convertedDrinkList = new ArrayList<>();
            for (Integer drinkId : drinkIdList) {
                convertedDrinkList.add(
                        drinkRepository.findById(drinkId)
                                .orElseThrow(() -> new DrinkNotFoundException(
                                        "cannot find drink with this id. Try again")));
            }
            if(!convertedDrinkList.isEmpty()) {
                checkProductQuantity(convertedDrinkList.get(0));
            }
            return (convertedDrinkList);
        }

        public List<Snack> getConvertedSnackList(List<Integer> snackIdList) {
            List<Snack> convertedSnackList = new ArrayList<>();
            for (Integer snackId : snackIdList) {
                convertedSnackList.add(
                        snackRepository.findById(snackId)
                                .orElseThrow(() -> new SnackNotFoundException(
                                        "cannot find drink with this id. Try again")));
            }
           if(!convertedSnackList.isEmpty()) {
               checkProductQuantity(convertedSnackList.get(0));
           }
            return (convertedSnackList);
        }

    public Page<PizzaDto> convertPizzaListToDto(Pageable pageable) {
        Page<Pizza> page = pizzaRepository.findAll(pageable);

        Page<PizzaDto> pizzaDtoPage = page.map(pizza -> {
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizza.getId());
            pizzaDto.setName(pizza.getName());
            pizzaDto.setDescription(pizza.getDescription());
            pizzaDto.setCost(pizza.getCost());
            pizzaDto.setPathToImage(pizza.getPathToImage());
            pizzaDto.setPortions(convertPortionsListToDto(pizza.getPortions()));
            return pizzaDto;
        });

        return pizzaDtoPage;
    }

        private List<PortionDto> convertPortionsListToDto(List<Portion> portions) {
            List<PortionDto> dtos = new ArrayList<>();
            for(Portion portion : portions) {
                PortionDto portionDto = new PortionDto();
                portionDto.setId(portion.getId());
                portionDto.setWeight(portion.getWeight());
                portionDto.setIngredientId(portion.getIngredient().getId());
                portionDto.setIngredientName(portion.getIngredient().getName());
                dtos.add(portionDto);
            }
            return dtos;
        }

    public Page<SnackDto> convertSnackListToDto(Pageable pageable) {
        Page<Snack> snacks = snackRepository.findAll(pageable);

        // Проверяем, если страница пуста, то выбрасываем исключение
        if (snacks.isEmpty()) {
            throw new SnackNotFoundException("Cannot find snacks");
        }

        // Преобразование каждой Snack в SnackDto с использованием метода map
        return snacks.map(snack -> {
            SnackDto snackDto = new SnackDto();
            snackDto.setId(snack.getId());
            snackDto.setDescription(snack.getDescription());
            snackDto.setName(snack.getName());
            snackDto.setPathToImage(snack.getPathToImage());
            snackDto.setCost(snack.getCost());
            snackDto.setWeight(snack.getWeight());
            return snackDto;
        });
    }

    public Page<DrinkDto> convertDrinkListToDto(Pageable pageable) {
        Page<Drink> drinks = drinkRepository.findAll(pageable);

        // Проверяем, если страница пуста, то выбрасываем исключение
        if (drinks.isEmpty()) {
            throw new DrinkNotFoundException("Cannot find drinks");
        }

        // Преобразование каждой Drink в DrinkDto с использованием метода map
        return drinks.map(drink -> {
            DrinkDto drinkDto = new DrinkDto();
            drinkDto.setId(drink.getId());
            drinkDto.setDescription(drink.getDescription());
            drinkDto.setName(drink.getName());
            drinkDto.setPathToImage(drink.getPathToImage());
            drinkDto.setCost(drink.getCost());
            drinkDto.setTaste(drink.getTaste());
            drinkDto.setVolume(drink.getVolume());
            return drinkDto;
        });
    }


    private void checkProductQuantity(Product product) {
            if(product.getQuantity() < 100) {
                notificationService.sendNotificationToAdmin(product);
            }
        }



}
