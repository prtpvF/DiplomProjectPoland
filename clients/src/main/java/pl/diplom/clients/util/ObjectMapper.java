package pl.diplom.clients.util;

import org.springframework.stereotype.Component;
import pl.diplom.clients.dto.*;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapper {

        public PersonOrderDtoResponse convertPersonOrderToDto(PersonOrder personOrder) {
            PersonOrderDtoResponse personOrderDto = new PersonOrderDtoResponse();
            personOrderDto.setPerson(personOrder.getPerson().getId());
            personOrderDto.setCost(personOrder.getCost());
            personOrderDto.setStatus(personOrder.getStatus());
           if(!personOrder.getPizzas().isEmpty()) {
               personOrderDto.setPizzas(getAllPizzaIdList(personOrder));
           }
           if(!personOrder.getSnacks().isEmpty()) {
               personOrderDto.setSnacks(getAllSnackIdList(personOrder));
           }
           if(!personOrder.getDrinks().isEmpty()) {
               personOrderDto.setDrinks(getAllDrinksIdList(personOrder));
           }
           personOrderDto.setAddress(personOrder.getAddress().getAddress());
           return personOrderDto;
        }

        private List<PizzaDto> getAllPizzaIdList(PersonOrder personOrder) {
            List<PizzaDto> pizzaIdList = new ArrayList<>();
            for(Pizza pizza : personOrder.getPizzas()) {
                pizzaIdList.add(convertPizzaToDto(pizza));
            }
            return pizzaIdList;
        }

        private List<SnackDto> getAllSnackIdList(PersonOrder personOrder) {
            List<SnackDto> snackIdList = new ArrayList<>();

            for(Snack snack : personOrder.getSnacks()) {
                snackIdList.add(convertSnackToDto(snack));
            }
            return snackIdList;
        }

    private List<DrinkDto> getAllDrinksIdList(PersonOrder personOrder) {
            List<DrinkDto> drunkIdList = new ArrayList<>();

            for(Drink drink : personOrder.getDrinks()) {
                drunkIdList.add(convertDrinkToDto(drink));
            }
            return drunkIdList;
        }

        private PizzaDto convertPizzaToDto(Pizza pizza) {
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizza.getId());
            pizzaDto.setName(pizza.getName());
            pizzaDto.setCost(pizza.getCost());
            return pizzaDto;
        }

        private SnackDto convertSnackToDto(Snack snack) {
            SnackDto snackDto = new SnackDto();
            snackDto.setId(snack.getId());
            snackDto.setName(snack.getName());
            snackDto.setCost(snack.getCost());
            return snackDto;
        }

        private DrinkDto convertDrinkToDto(Drink drink) {
            DrinkDto snackDto = new DrinkDto();
            snackDto.setId(drink.getId());
            snackDto.setName(drink.getName());
            snackDto.setCost(drink.getCost());
            return snackDto;
        }
}