package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.clients.dto.PersonOrderDtoResponse;
import pl.diplom.clients.exception.CannotDeleteOrderException;
import pl.diplom.clients.exception.IllegalOrderOwnerException;
import pl.diplom.clients.exception.PersonOrderNotFoundException;
import pl.diplom.clients.util.ObjectMapper;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Product;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.AddressRepository;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class PersonOrderService {

        private final PersonOrderRepository personOrderRepository;
        private final ProductService productService;
        private final AddressRepository addressRepository;
        private final ObjectMapper objectMapper;
        private final AddressService addressService;
    private final PizzaRepository pizzaRepository;
    private final SnackRepository snackRepository;
    private final DrinkRepository drinkRepository;

    public HttpStatus createOrder(PersonOrderDto personOrder,
                                      Person person) {
            isPersonOrderDataValid(personOrder);
            PersonOrder order = new PersonOrder();
            order.setPerson(person);
            order.setStatus(PersonOrderStatusEnum.ACCEPTED.name());

            setProducts(personOrder, order);

            order.setCost(calculateOrderTotalPrice(order));
            order.setAddress(addressRepository.findByAddressAndPerson(personOrder.getAddress(), person).get());
            personOrderRepository.save(order);
            return CREATED;
        }

         public HttpStatus deleteOrder(Integer personOrderId, Person person) {
            PersonOrder personOrder = getPersonOrderById(personOrderId);
            isOrderBelongsToUser(person, personOrder);
            isPersonOrderCanBeDeleted(personOrder);
            personOrderRepository.delete(personOrder);
            return OK;
        }

        public Page<PersonOrderDtoResponse> getPersonOrderHistoryAsDtoList(Person person,
                                                                   Pageable pageable) {
            Page<PersonOrder> foundedOrders = personOrderRepository
                    .findByPerson(person, pageable);
            Page<PersonOrderDtoResponse> dtos = foundedOrders.map(objectMapper::convertPersonOrderToDto);
            return dtos;
        }

        public PersonOrderDtoResponse getPersonOrder(Integer personOrderId) {
            PersonOrder order = getPersonOrderById(personOrderId);
            return objectMapper.convertPersonOrderToDto(order);
        }

        private void setProducts(PersonOrderDto personOrderDto, PersonOrder order) {
            setPizzaIfNotEmpty(personOrderDto, order);
            setDrinksIfNotEmpty(personOrderDto, order);
            setSnackIfNotEmpty(personOrderDto, order);
        }

        private void setDrinksIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getDrinks().isEmpty()) {
                order.setDrinks(productService
                        .getConvertedDrinkList(personOrder
                                .getDrinks()));
            }

            for(Integer id : personOrder.getDrinks()) {
                Drink drink = drinkRepository.findById(id).get();
                drink.setQuantity(drink.getQuantity()-1);
                drinkRepository.save(drink);
            }
        }

        private void setPizzaIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getPizzas().isEmpty()) {
                order.setPizzas(productService
                        .getConvertedPizzaList(personOrder
                                .getPizzas()));
            }
        }

        private void setSnackIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getSnacks().isEmpty()) {
                order.setSnacks(productService
                        .getConvertedSnackList(personOrder
                                .getSnacks()));
            }

            for(Integer id : personOrder.getSnacks()) {
                Snack snack = snackRepository.findById(id).get();
                snack.setQuantity(snack.getQuantity()-1);
                snackRepository.save(snack);
            }
        }

        private double calculateOrderTotalPrice(PersonOrder personOrder) {
            double sum = 0;

            List<Product> productsInOrder = new ArrayList<>();
            Optional.of(personOrder.getDrinks()).filter(drinks -> !drinks.isEmpty()).ifPresent(productsInOrder::addAll);
            Optional.of(personOrder.getPizzas()).filter(pizzas -> !pizzas.isEmpty()).ifPresent(productsInOrder::addAll);
            Optional.of(personOrder.getSnacks()).filter(snacks -> !snacks.isEmpty()).ifPresent(productsInOrder::addAll);

            for (Product product : productsInOrder) {
                sum += product.getCost();
            }
            return sum;
        }

        private PersonOrder getPersonOrderById(int personOrderId) {
            return personOrderRepository.findById(personOrderId)
                    .orElseThrow(() -> new PersonOrderNotFoundException("cannot find order"));
        }

        private void isOrderBelongsToUser(Person person, PersonOrder order) {
            if (!order.getPerson().equals(person)) {
                throw new IllegalOrderOwnerException("you are not owner of this order");
            }
        }

        /**
         * Method checks possibility of deleting person order.
         * Person order cannot be deleted if status is higher than ACCEPTED
         * look PersonOrderEnum for hierarchy of statuses
         *
         * @param personOrder model of client's order
         */
        private void isPersonOrderCanBeDeleted(PersonOrder personOrder) {
            if (!personOrder
                    .getStatus()
                    .equals(PersonOrderStatusEnum.ACCEPTED.name())) {
                throw new CannotDeleteOrderException("you order in process, you can't decline it");
            }
        }

        private void isPersonOrderDataValid(PersonOrderDto personOrderDto) {
            if(personOrderDto.getDrinks().isEmpty()
                    && personOrderDto.getPizzas().isEmpty()
                && personOrderDto.getSnacks().isEmpty()) {
                throw new IllegalArgumentException("you can not create an order without any products");
            }
        }
}