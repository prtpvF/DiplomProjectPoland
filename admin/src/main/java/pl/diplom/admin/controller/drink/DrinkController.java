package pl.diplom.admin.controller.drink;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.diplom.admin.dto.DrinkDto;
import pl.diplom.admin.service.ProductService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DrinkController {

    private final ProductService productService;

    @PostMapping(value = "/drink/create")
    public HttpStatus createDrink(@ModelAttribute DrinkDto drinkDto) throws IOException {
        productService.createDrink(drinkDto, drinkDto.getImage());
        return CREATED;
    }

    @PatchMapping("/drink/{id}")
    public HttpStatus updateDrink(@PathVariable("id") Integer drinkId,
                                  @RequestBody DrinkDto drinkDto) {
         productService.updateDrink(drinkId, drinkDto);
         return CREATED;
    }

    @DeleteMapping("/drink/{id}")
    public HttpStatus deleteDrink(@PathVariable("id") Integer drinkId) {
        productService.deleteDrink(drinkId);
        return CREATED;
    }


}
