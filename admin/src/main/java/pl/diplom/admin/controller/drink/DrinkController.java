package pl.diplom.admin.controller.drink;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.DrinkDto;
import pl.diplom.admin.service.ProductService;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.repository.product.DrinkRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DrinkController {

    private final ProductService productService;
    private final DrinkRepository drinkRepository;

    @PostMapping("/drink/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus createDrink(@RequestPart("drinkDto") DrinkDto drinkDto,
                                  @RequestPart("image") MultipartFile image) throws IOException {
        productService.createDrink(drinkDto, image);
        return HttpStatus.CREATED;
    }

    @PatchMapping("/drink/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus updateDrink(@PathVariable("id") Integer drinkId,
                                  @RequestBody DrinkDto drinkDto) {
        return productService.updateDrink(drinkId, drinkDto);
    }

    @PostMapping("/delete/drink/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus deleteDrink(@PathVariable("id") Integer drinkId) {
        productService.deleteDrink(drinkId);
        return HttpStatus.OK;
    }
}
