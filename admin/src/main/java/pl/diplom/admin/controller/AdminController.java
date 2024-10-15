package pl.diplom.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.*;
import pl.diplom.admin.dto.worker.RegistrationDto;
import pl.diplom.admin.dto.worker.UpdateWorkerDto;
import pl.diplom.admin.service.AdminService;
import pl.diplom.admin.service.IngredientService;
import pl.diplom.admin.service.PersonService;
import pl.diplom.admin.service.ProductService;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.RoleRepository;
import pl.diplom.common.repository.product.DrinkRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.common.repository.product.SnackRepository;
import pl.diplom.security.util.PersonDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;
        private final PersonService personService;
        private final IngredientService ingredientService;

        @PostMapping("/ingredient")
        public ResponseEntity createIngredient(@RequestBody IngredientDto ingredientDto) {

            return new ResponseEntity("Ingredient created",
                    adminService.createIngredient(ingredientDto));
        }

        @DeleteMapping("/ingredient/{id}")
        public HttpStatus deleteIngredient(@PathVariable("id") Integer id) {
            return adminService.deleteIngredient(id);
        }

        @GetMapping("/ingredients")
        public Page<IngredientDto> allIngredient(@RequestHeader("token") String token, Pageable pageable) { //pagination
            return ingredientService.getAllIngredients(pageable);
        }

        @GetMapping("/ingredient/{id}")
        public IngredientDto getIngredient(@PathVariable("id") Integer id) {
          return ingredientService.getIngredientById(id);
        }

        @PatchMapping("/ingredient/{id}")
        public HttpStatus updateIngredient(@PathVariable("id") Integer orderNeedTobeUpdatedId,
                                           @RequestBody IngredientDto ingredientDto) {
            return  adminService.updateIngredient(ingredientDto, orderNeedTobeUpdatedId);
        }

        @PatchMapping("/order/address/{id}")
        public HttpStatus updateAddress(@PathVariable("id") Integer orderNeedToBeUpdatedId,
                                        @RequestParam String address) {
            return adminService.updateAddressForOrder(orderNeedToBeUpdatedId, address);
        }

        @PatchMapping("/order/status/{id}")
        public HttpStatus updateOrderStatus(@PathVariable("id") Integer orderId,
                                            @RequestParam String status) {
            return adminService.updatePersonOrderStatus(orderId, status);
        }

        @GetMapping("/persons")
        public List<PersonDto> allPersonPage() {
           return personService.getAllWorkers();
        }

        @GetMapping("/findByUsername")
        public PersonDto findPersonByUsername(@RequestParam String username) {
            return personService.findByUsername(username);
        }

        @DeleteMapping("/person-order/{id}")
        public HttpStatus deletePersonOrder(@PathVariable("id") Integer id) {
            return adminService.removeOrderFromStory(id);
        }

        @GetMapping("/test")
        public  ResponseEntity<String> admin(@RequestHeader("token") String token) {
            return ResponseEntity.ok("Token received: " + token);
        }

        @PatchMapping("/ban/{id}")
        public HttpStatus banPerson(@PathVariable("id") Integer personId) {
            adminService.banPerson(personId);
            return HttpStatus.OK;
        }

        @PatchMapping("/unban/{id}")
        public HttpStatus unbanPerson(@PathVariable("id") Integer personId) {
            adminService.unbanPerson(personId);
            return HttpStatus.OK;
        }

        @PostMapping("/new/worker")
        public HttpStatus createNewWorker(@RequestBody RegistrationDto registrationDto,
                                          BindingResult bindingResult) {
            if (!bindingResult.hasErrors()) {
                return personService.createNewWorker(registrationDto);
            }
            else {
                return HttpStatus.BAD_REQUEST;
            }
        }

        @DeleteMapping("/worker/{id}")
        public HttpStatus deleteWorker(@PathVariable("id") Integer workerId) {
            return personService.deleterWorker(workerId);
        }

        @PatchMapping("/worker/{id}")
        public HttpStatus updateWorker(@PathVariable("id") Integer workerId,
                                       @RequestBody UpdateWorkerDto updateWorkerDto) {
            return  personService.updateWorker(workerId, updateWorkerDto);
        }
}