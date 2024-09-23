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
import pl.diplom.clients.service.ClientService;
import pl.diplom.clients.service.PersonOrderService;
import pl.diplom.clients.service.PersonService;
import pl.diplom.clients.service.ProductService;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.security.util.PersonDetails;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final ClientService clientService;
    private final PersonOrderService personOrderService;
    private final PersonService personService;
    private final ProductService productService;

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
    public void createOrder(@RequestBody PersonOrderDto order) {
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

    @PostMapping("/address")
    public void addAddress(@RequestHeader("token") String token,
                           @RequestBody String address) {
        clientService.addAddress(token, address);
    }

    private void setAuth(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String username = personDetails.getUsername();
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
    }
}