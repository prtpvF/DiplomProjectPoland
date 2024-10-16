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
         productService.deleteSnack(snackId);
        return HttpStatus.CREATED;
    }

    @PatchMapping("/snack/{id}")
    public HttpStatus updateSnack(@PathVariable("id") Integer snackId,
                                  @RequestBody SnackDto snackDto) {
         productService.updateSnack(snackId, snackDto);
        return HttpStatus.CREATED;
    }

    @PostMapping("/snack/create")
    public HttpStatus createSnack(@ModelAttribute SnackDto snackDto) throws IOException {
         productService.createSnack(snackDto, snackDto.getImage());
        return HttpStatus.CREATED;
    }
}