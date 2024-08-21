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
import pl.diplom.admin.service.AdminService;
import pl.diplom.admin.service.ProductService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;
        private final ProductService productService;

        @PostMapping("/ingredient")
        public ResponseEntity createIngredient(@RequestBody IngredientDto ingredientDto,
                                               BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity("both fields must be filled",
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("Ingredient created",
                    adminService.createIngredient(ingredientDto));
        }

        @DeleteMapping("/ingredient/{id}")
        public HttpStatus deleteIngredient(@PathVariable("id") Integer id) {
            return adminService.deleteIngredient(id);
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

        @PostMapping(value = "/pizza", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                                      MediaType.APPLICATION_OCTET_STREAM_VALUE})
        public HttpStatus createPizza(@RequestPart("pizzaDto") PizzaDto pizzaDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createPizza(pizzaDto, image);
            return HttpStatus.CREATED;
        }
        @PostMapping("/drink")
        public HttpStatus createDrink(@RequestPart("drinkDto") DrinkDto drinkDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createDrink(drinkDto, image);
            return HttpStatus.CREATED;
        }

        @PostMapping("/snack")
        public HttpStatus createSnack(@RequestPart("snackDto") SnackDto snackDto,
                                      @RequestPart("image") MultipartFile image) {
            productService.createSnack(snackDto, image);
            return HttpStatus.CREATED;
        }

        @DeleteMapping("/person-order/{id}")
        public HttpStatus deletePersonOrder(@PathVariable("id") Integer id) {
            return adminService.removeOrderFromStory(id);
        }

        @GetMapping("/person/{id}")
        public PersonDto getPerson(@PathVariable Integer id) {
            return adminService.getPersonDtoById(id);
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
}