package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.clients.exception.CannotDeleteOrderException;
import pl.diplom.clients.exception.IllegalOrderOwnerException;
import pl.diplom.clients.exception.PersonOrderNotFoundException;
import pl.diplom.clients.util.ObjectMapper;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.model.product.Product;
import pl.diplom.common.repository.PersonOrderRepository;

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
        private final ObjectMapper objectMapper;
        private final AddressService addressService;

        public HttpStatus createOrder(PersonOrderDto personOrder,
                                      Person person) {
            isPersonOrderDataValid(personOrder);
            PersonOrder order = new PersonOrder();
            order.setPerson(person);
            order.setStatus(PersonOrderStatusEnum.ACCEPTED.name());

            setProducts(personOrder, order);

            order.setCost(calculateOrderTotalPrice(order));
            order.setAddress(addressService.findOrCreateAddress(personOrder.getAddress(), person));
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

        public Page<PersonOrderDto> getPersonOrderHistoryAsDtoList(Person person,
                                                                   Pageable pageable) {
            Page<PersonOrder> foundedOrders = personOrderRepository
                    .findByPerson(person, pageable);
            Page<PersonOrderDto> dtos = foundedOrders.map(objectMapper::convertPersonOrderToDto);
            return dtos;
        }

        public PersonOrderDto getPersonOrder(Integer personOrderId) {
            PersonOrder order = getPersonOrderById(personOrderId);
            return objectMapper.convertPersonOrderToDto(order);
        }

        private void setProducts(PersonOrderDto personOrderDto, PersonOrder order) {
            setPizzaIfNotEmpty(personOrderDto, order);
            setDrinksIfNotEmpty(personOrderDto, order);
            setSnackIfNotEmpty(personOrderDto, order);
        }

        private void setDrinksIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getDrinksIdList().isEmpty()) {
                order.setDrinks(productService
                        .getConvertedDrinkList(personOrder
                                .getDrinksIdList()));
            }
        }

        private void setPizzaIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getPizzaIdList().isEmpty()) {
                order.setPizzas(productService
                        .getConvertedPizzaList(personOrder
                                .getPizzaIdList()));
            }
        }

        private void setSnackIfNotEmpty(PersonOrderDto personOrder, PersonOrder order) {
            if(!personOrder.getSnacksIdList().isEmpty()) {
                order.setSnacks(productService
                        .getConvertedSnackList(personOrder
                                .getSnacksIdList()));
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
            if(personOrderDto.getDrinksIdList().isEmpty()
                    && personOrderDto.getPizzaIdList().isEmpty()
                && personOrderDto.getSnacksIdList().isEmpty()) {
                throw new IllegalArgumentException("you can not create an order without any products");
            }
        }
}