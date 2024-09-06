package pl.diplom.clients.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.diplom.clients.dto.DrinkDto;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.clients.dto.PizzaDto;
import pl.diplom.clients.dto.SnackDto;
import pl.diplom.clients.service.ClientService;
import pl.diplom.clients.service.PersonOrderService;
import pl.diplom.clients.service.PersonService;
import pl.diplom.clients.service.ProductService;
import pl.diplom.common.model.Person;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

        private final ClientService clientService;
        private final PersonOrderService personOrderService;
        private final PersonService personService;
        private final ProductService productService;

        @GetMapping("/pizza")
        public Page<PizzaDto> getAllPizza(Pageable pageable) {
            return productService.convertPizzaListToDto(pageable);
        }

        @GetMapping("/drinks")
        public Page<DrinkDto> getAllDrinks(Pageable pageable) {
            return productService.convertDrinkListToDto(pageable);
        }

        @GetMapping("/snacks")
        public Page<SnackDto> getAllSnacks(Pageable pageable) {
            return productService.convertSnackListToDto(pageable);
        }

        @PostMapping("/order")
        public void createOrder(@RequestHeader("token") String token,
                                @RequestBody PersonOrderDto order) {
            clientService.createOrder(token, order);
        }

        @GetMapping("/order/history")
        public Page<PersonOrderDto> getAllOrders(@RequestHeader("token") String token,
                                                 Pageable pageable) {
            Person person = personService.getPersonFromToken(token);
            return clientService.findAllPersonOrders(person, pageable);
        }

        @GetMapping("/order/{id}")
        public PersonOrderDto getPersonOrderDto(@PathVariable("id") Integer id) {
            return personOrderService.getPersonOrder(id);
        }

        @DeleteMapping("/order/{id}")
        public void deletePersonOrder(@PathVariable("id") int personOrderId,
                                      @RequestHeader("token") String token){
            clientService.deleteOrder(token, personOrderId);
        }

        @PostMapping("/address")
        public void addAddress(@RequestHeader("token") String token,
                               @RequestBody String address) {
            clientService.addAddress(token, address);
        }
}