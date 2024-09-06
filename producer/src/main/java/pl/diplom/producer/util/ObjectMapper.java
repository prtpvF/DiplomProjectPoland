package pl.diplom.producer.util;

import org.springframework.stereotype.Component;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.producer.dto.*;

@Component
public class ObjectMapper {

        public PersonOrderDto convertPersonOrderToDto(PersonOrder personOrder) {
            PersonOrderDto personOrderDto = new PersonOrderDto();
            personOrderDto.setId(personOrder.getId());
            personOrderDto.setPerson(convertPersonDtoToPersonDto(personOrder.getPerson()));
            personOrderDto.setSnacks();
            personOrderDto.setDrinks();
            personOrderDto.setPizzas();
            personOrderDto.setAddress(personOrder.getAddress().getAddress());
        }

        public PersonDto convertPersonDtoToPersonDto(Person person) {
            PersonDto personDto = new PersonDto();
            personDto.setId(person.getId());
            personDto.setEmail(person.getEmail());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            return personDto;
        }

        public PizzaDto convertPizzaToDto(Pizza pizza) {
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizza.getId());
            pizzaDto.setName(pizza.getName());
            pizzaDto.setCost(pizzaDto.getCost());
            return pizzaDto;
        }

        public DrinkDto convertDrinkToDto(Drink drink) {
            DrinkDto drinkDto = new DrinkDto();
            drinkDto.setId(drink.getId());
            drinkDto.setName(drink.getName());
            drinkDto.setCost(drink.getCost());
            drinkDto.setVolume(drink.getVolume());
            drinkDto.setTaste(drink.getTaste());
            return drinkDto;
        }

        public SnackDto convertSnackToDto(Snack snack) {
            SnackDto snackDto = new SnackDto();
            snackDto.setId(snack.getId());
            snackDto.setCost(snack.getCost());
            snackDto.setWeight(snack.getWeight());
            snackDto.setName(snack.getName());
            return snackDto;
        }
}
