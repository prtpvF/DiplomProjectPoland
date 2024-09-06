package pl.diplom.clients.util;

import org.springframework.stereotype.Component;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapper {

        public PersonOrderDto convertPersonOrderToDto(PersonOrder personOrder) {
            PersonOrderDto personOrderDto = new PersonOrderDto();
            personOrderDto.setPerson(personOrder.getPerson().getId());
            personOrderDto.setCost(personOrder.getCost());
            personOrderDto.setStatus(personOrder.getStatus());
           if(!personOrder.getPizzas().isEmpty()) {
               personOrderDto.setPizzaIdList(getAllPizzaIdList(personOrder));
           }
           if(!personOrder.getSnacks().isEmpty()) {
               personOrderDto.setSnacksIdList(getAllSnackIdList(personOrder));
           }
           if(!personOrder.getDrinks().isEmpty()) {
               personOrderDto.setDrinksIdList(getAllDrinksIdList(personOrder));
           }
           personOrderDto.setAddress(personOrder.getAddress().getAddress());
           return personOrderDto;
        }

        private List<Integer> getAllPizzaIdList(PersonOrder personOrder) {
            List<Integer> pizzaIdList = new ArrayList<>();
            for(Pizza pizza : personOrder.getPizzas()) {
                pizzaIdList.add(pizza.getId());
            }
            return pizzaIdList;
        }

        private List<Integer> getAllSnackIdList(PersonOrder personOrder) {
            List<Integer> snackIdList = new ArrayList<>();

            for(Snack snack : personOrder.getSnacks()) {
                snackIdList.add(snack.getId());
            }
            return snackIdList;
        }

        private List<Integer> getAllDrinksIdList(PersonOrder personOrder) {
            List<Integer> drunkIdList = new ArrayList<>();

            for(Drink drink : personOrder.getDrinks()) {
                drunkIdList.add(drink.getId());
            }
            return drunkIdList;
        }
}