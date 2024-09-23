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

    public Page<Pizza> convertPizzaListToDto(Pageable pageable) {
        return pizzaRepository.findAll(pageable);
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

    public Page<Snack> convertSnackListToDto(Pageable pageable) {
        Page<Snack> snacks = snackRepository.findAll(pageable);

        // Проверяем, если страница пуста, то выбрасываем исключение
        if (snacks.isEmpty()) {
            throw new SnackNotFoundException("Cannot find snacks");
        }
        return snacks;
    }

    public Page<Drink> convertDrinkListToDto(Pageable pageable) {
        Page<Drink> drinks = drinkRepository.findAll(pageable);

        // Проверяем, если страница пуста, то выбрасываем исключение
        if (drinks.isEmpty()) {
            throw new DrinkNotFoundException("Cannot find drinks");
        }
        return drinks;
    }


    private void checkProductQuantity(Product product) {
            if(product.getQuantity() < 100) {
                notificationService.sendNotificationToAdmin(product);
            }
        }



}
