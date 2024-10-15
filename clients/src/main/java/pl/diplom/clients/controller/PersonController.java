package pl.diplom.clients.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.diplom.clients.dto.DrinkDto;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.clients.dto.PizzaDto;
import pl.diplom.clients.dto.SnackDto;
import pl.diplom.clients.service.*;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.security.util.PersonDetails;

import java.util.List;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final ClientService clientService;
    private final PersonOrderService personOrderService;
    private final PersonService personService;
    private final ProductService productService;
    private final AddressService addressService;

    @GetMapping("/pizza")
    public String getAllPizza(Pageable pageable, Model model) {
        Page<Pizza> pizzaPage = productService.convertPizzaListToDto(pageable);
        model.addAttribute("pizza", pizzaPage);
        return "/pizza/all";
    }

    @GetMapping("/drinks")
    public String getAllDrinks(Pageable pageable, Model model) {
        Page<Drink> drinkPage = productService.convertDrinkListToDto(pageable);
        model.addAttribute("drinks", drinkPage);
        return "/drink/all";
    }

    @GetMapping("/snacks")
    public String getAllSnacks(Pageable pageable, Model model) {
        Page<Snack> snackPage = productService.convertSnackListToDto(pageable);
        model.addAttribute("snacks", snackPage);
        return "/snack/all";
    }

    @PostMapping("/order")
    public void createOrder(@RequestBody PersonOrderDto order,
                            @RequestHeader("token") String token) {
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
                                  @RequestHeader("token") String token) {
        clientService.deleteOrder(token, personOrderId);
    }
}