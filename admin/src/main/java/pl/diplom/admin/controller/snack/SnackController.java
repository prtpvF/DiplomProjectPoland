package pl.diplom.admin.controller.snack;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.diplom.admin.dto.SnackDto;
import pl.diplom.admin.service.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SnackController {

    private final ProductService productService;

    @DeleteMapping("/snack/{id}")
    public HttpStatus deleteSnack(@PathVariable("id") Integer snackId) {
        return productService.deleteSnack(snackId);
    }

    @PatchMapping("/snack/{id}")
    public HttpStatus updateSnack(@PathVariable("id") Integer snackId,
                                  @RequestBody SnackDto snackDto) {
        return productService.updateSnack(snackId, snackDto);
    }

    @PostMapping("/snack/create")
    public HttpStatus createSnack(@ModelAttribute SnackDto snackDto) throws IOException {
        return productService.createSnack(snackDto, snackDto.getImage());
    }
}