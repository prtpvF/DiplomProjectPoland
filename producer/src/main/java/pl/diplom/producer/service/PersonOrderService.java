package pl.diplom.producer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;
import pl.diplom.producer.dto.DrinkDto;
import pl.diplom.producer.dto.PersonOrderDto;
import pl.diplom.producer.dto.PizzaDto;
import pl.diplom.producer.dto.SnackDto;
import pl.diplom.security.jwt.JwtUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonOrderService {

        private final PersonOrderRepository personOrderRepository;
        private final PizzaRepository pizzaRepository;
        private final DrinkRepository drinkRepository;
        private final SnackRepository snackRepository;
        //private final Stat

        public PersonOrder getPersonOrderById(Integer orderId) {
               return personOrderRepository.findById(orderId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find order with this id"));
        }

//        public List<PersonOrderDto> getOrderDtoList() {
//
//            Page<PersonOrder> page = personOrderRepository.findByStatus();
//        }

        public HttpStatus save(PersonOrder personOrder) {
            personOrderRepository.save(personOrder);
            return HttpStatus.OK;
        }

        public PersonOrderDto convertToDto(PersonOrder personOrder) {
            PersonOrderDto personOrderDto = new PersonOrderDto();
            personOrderDto.setId(personOrder.getId());
            personOrderDto.setUsername(personOrder.getPerson().getUsername());
            personOrderDto.setAddress(personOrder.getAddress().getAddress());
            if(personOrder.getPizzas().size() > 0) {
                personOrderDto.setPizzas(convertAllPizzaFromPersonOrder(personOrder));
            }
            if(personOrder.getDrinks().size() > 0) {
                personOrderDto.setDrinks(convertAllDrinksFromPersonOrder(personOrder));
            }
            if(personOrder.getSnacks().size() > 0) {
                personOrderDto.setSnacks(convertAllSnackFromPersonOrder(personOrder));
            }
            personOrderDto.setStatus(personOrder.getStatus());
            return personOrderDto;
        }

        private List<PizzaDto> convertAllPizzaFromPersonOrder(PersonOrder order) {
            List<PizzaDto> pizzaDtos = new ArrayList<>();
            if(order.getPizzas().size()>0) {
                List<Pizza> pizzaList = order.getPizzas();

                for (Pizza pizza : pizzaList) {
                    pizzaDtos.add(convertPizzaToDto(pizza));
                }
            }
            else {
                return null;
            }
            return pizzaDtos;
        }

        private List<DrinkDto> convertAllDrinksFromPersonOrder(PersonOrder order) {
            List<DrinkDto> drinkDtos = new ArrayList<>();
            if(order.getDrinks().size()>0) {
                List<Drink> drinkList = order.getDrinks();

                for (Drink drink : drinkList) {
                    drinkDtos.add(convertDrinkToDto(drink));
                }
            }
            else {
                return null;
            }
            return drinkDtos;
        }

        private List<SnackDto> convertAllSnackFromPersonOrder(PersonOrder order) {
            List<SnackDto> snackDtos = new ArrayList<>();
            if(order.getSnacks().size()>0) {
                List<Snack> snacks = order.getSnacks();

                for (Snack snack : snacks) {
                    snackDtos.add(convertSnackToDto(snack));
                }
            }
            else {
                return null;
            }
            return snackDtos;
        }

        private PizzaDto convertPizzaToDto(Pizza pizza) {
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizza.getId());
            pizzaDto.setName(pizza.getName());
            pizzaDto.setCost(pizza.getCost());
            return pizzaDto;
        }

        private DrinkDto convertDrinkToDto(Drink drink) {
            DrinkDto dto = new DrinkDto();
            dto.setId(drink.getId());
            dto.setName(drink.getName());
            dto.setCost(drink.getCost());
            dto.setVolume(drink.getVolume());
            dto.setTaste(drink.getTaste());
            return dto;
        }

        private SnackDto convertSnackToDto(Snack snack) {
            SnackDto snackDto = new SnackDto();
            snackDto.setId(snack.getId());
            snackDto.setName(snack.getName());
            snackDto.setCost(snack.getCost());
            snackDto.setWeight(snack.getWeight());
            return snackDto;
        }
}