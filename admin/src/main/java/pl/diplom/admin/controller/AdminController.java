package pl.diplom.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.*;
import pl.diplom.admin.dto.worker.RegistrationDto;
import pl.diplom.admin.dto.worker.UpdateWorkerDto;
import pl.diplom.admin.service.AdminService;
import pl.diplom.admin.service.PersonService;
import pl.diplom.admin.service.ProductService;
import pl.diplom.common.model.product.Pizza;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;
        private final ProductService productService;
        private final PersonService personService;

        @PostMapping("/ingredient")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public ResponseEntity createIngredient(@RequestBody IngredientDto ingredientDto,
                                               BindingResult bindingResult) {

            return new ResponseEntity("Ingredient created",
                    adminService.createIngredient(ingredientDto));
        }

        @DeleteMapping("/ingredient/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus deleteIngredient(@PathVariable("id") Integer id) {
            return adminService.deleteIngredient(id);
        }

        @PatchMapping("/ingredient/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updateIngredient(@PathVariable("id") Integer orderNeedTobeUpdatedId,
                                           @RequestBody IngredientDto ingredientDto) {
            return  adminService.updateIngredient(ingredientDto, orderNeedTobeUpdatedId);
        }

        @PatchMapping("/order/address/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updateAddress(@PathVariable("id") Integer orderNeedToBeUpdatedId,
                                        @RequestParam String address) {
            return adminService.updateAddressForOrder(orderNeedToBeUpdatedId, address);
        }

        @PatchMapping("/order/status/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updateOrderStatus(@PathVariable("id") Integer orderId,
                                            @RequestParam String status) {
            return adminService.updatePersonOrderStatus(orderId, status);
        }

        @PostMapping(value = "/pizza", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      MediaType.APPLICATION_OCTET_STREAM_VALUE})
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus createPizza(@RequestPart("pizzaDto") PizzaDto pizzaDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createPizza(pizzaDto,image);
            return HttpStatus.CREATED;
        }

        @PostMapping("/drink")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus createDrink(@RequestPart("drinkDto") DrinkDto drinkDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createDrink(drinkDto, image);
            return HttpStatus.CREATED;
        }

        @PostMapping("/snack")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus createSnack(@RequestPart("snackDto") SnackDto snackDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createSnack(snackDto,image);
            return HttpStatus.CREATED;
        }

        @DeleteMapping("/person-order/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus deletePersonOrder(@PathVariable("id") Integer id) {
            return adminService.removeOrderFromStory(id);
        }

        @GetMapping("/person/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public PersonDto getPerson(@PathVariable Integer id) {
            return adminService.getPersonDtoById(id);
        }

        @PatchMapping("/ban/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus banPerson(@PathVariable("id") Integer personId) {
            adminService.banPerson(personId);
            return HttpStatus.OK;
        }

        @PatchMapping("/unban/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus unbanPerson(@PathVariable("id") Integer personId) {
            adminService.unbanPerson(personId);
            return HttpStatus.OK;
        }

        @PostMapping("/new/worker")
        @PreAuthorize("hasRole('SUPER_ADMIN')")
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
        @PreAuthorize("hasRole('SUPER_ADMIN')")
        public HttpStatus deleteWorker(@PathVariable("id") Integer workerId) {
            return personService.deleterWorker(workerId);
        }

        @PatchMapping("/worker/{id}")
        @PreAuthorize("hasRole('SUPER_ADMIN')")
        public HttpStatus updateWorker(@PathVariable("id") Integer workerId,
                                       @RequestBody UpdateWorkerDto updateWorkerDto) {
            return  personService.updateWorker(workerId, updateWorkerDto);
        }


        @PatchMapping("/drink/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updateDrink(@PathVariable("id") Integer drinkId,
                                      @RequestPart DrinkDto drinkDto,
                                      @RequestPart MultipartFile image) {
            return productService.updateDrink(drinkId, drinkDto, image);
        }

        @PatchMapping("/snack/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updateSnack(@PathVariable("id") Integer snackId,
                                      @RequestPart SnackDto snackDto,
                                      @RequestPart MultipartFile image) {
            return productService.updateSnack(snackId, snackDto, image);
        }

        @PatchMapping("/pizza/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus updatePizza(@PathVariable("id") Integer pizzaId,
                                      @RequestPart PizzaDto pizzaDto,
                                      @RequestPart MultipartFile image) {
            return productService.updatePizza(pizzaId, pizzaDto, image);
        }

        @DeleteMapping("/drink/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus deleteDrink(@PathVariable("id") Integer drinkId) {
            return productService.deleteDrink(drinkId);
        }

        @DeleteMapping("/snack/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus deleteSnack(@PathVariable("id") Integer snackId) {
            return productService.deleteSnack(snackId);
        }

        @DeleteMapping("/pizza/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
        public HttpStatus deletePizza(@PathVariable("id") Integer pizzaId) {
            return productService.deletePizza(pizzaId);
        }
}