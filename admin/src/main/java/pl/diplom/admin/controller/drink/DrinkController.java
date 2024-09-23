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

@Controller
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
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus updateDrink(@PathVariable("id") Integer drinkId,
                                  @ModelAttribute("drink") Drink drink) {
        return productService.updateDrink(drinkId, drink);
    }

    @PostMapping("/delete/drink/{id}")
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public String deleteDrink(@PathVariable("id") Integer drinkId) {
        productService.deleteDrink(drinkId);
        return "redirect:/drink/all";
    }

    @RequestMapping(value = "/all/drinks", method = RequestMethod.GET)
    public String allDrink(Model model) {
        List<Drink> drinks = drinkRepository.findAll();
        model.addAttribute("drinks", drinks);
        return "drink/all";
    }

    @GetMapping("/drink/{id}")
    public String getDrink(@PathVariable("id") Integer id, Model model) {
        setAuth(model);
        Drink drink = drinkRepository.findById(id).orElse(null);
        model.addAttribute("drink", drink);
        return "/drink/page";
    }

    @GetMapping("/update/drink/{id}/page")
    public String updateDrinkPage(@PathVariable("id") int id,
                                  Model model) {
        Optional<Drink> drink = drinkRepository.findById(id);
        model.addAttribute("drink", drink.get());
        return "/drink/update";
    }

    @GetMapping("/drink/create")
    public String createDrinkPage(Model model) {
        setAuth(model);
        model.addAttribute("drinkDto", new DrinkDto());
        return "drink/create";
    }

    private void setAuth(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("userRole", userRole);
    }

}
