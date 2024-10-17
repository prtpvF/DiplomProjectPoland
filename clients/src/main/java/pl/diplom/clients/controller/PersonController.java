package pl.diplom.clients.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.diplom.clients.dto.*;
import pl.diplom.clients.service.*;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.security.util.PersonDetails;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final ClientService clientService;
    private final PersonOrderService personOrderService;
    private final PersonService personService;
    private final ProductService productService;
    private final AddressService addressService;

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody PersonOrderDto order,
                                      @RequestHeader("token") String token) {
        clientService.createOrder(token, order);
        return new ResponseEntity(CREATED);
    }

    @GetMapping("/order/history")
    public Page<PersonOrderDtoResponse> getAllOrders(@RequestHeader("token") String token,
                                             Pageable pageable) {
        Person person = personService.getPersonFromToken(token);
        return clientService.findAllPersonOrders(person, pageable);
    }

    @GetMapping("/order/{id}")
    public PersonOrderDtoResponse getPersonOrderDto(@PathVariable("id") Integer id) {
        return personOrderService.getPersonOrder(id);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity deletePersonOrder(@PathVariable("id") int personOrderId,
                                  @RequestHeader("token") String token) {
        clientService.deleteOrder(token, personOrderId);
        return new ResponseEntity(OK);
    }
}