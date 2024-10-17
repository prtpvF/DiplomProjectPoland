package pl.diplom.admin.controller.snack;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.diplom.admin.dto.SnackDto;
import pl.diplom.admin.service.ProductService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SnackController {

    private final ProductService productService;

    @DeleteMapping("/snack/{id}")
    public ResponseEntity deleteSnack(@PathVariable("id") Integer snackId) {
         productService.deleteSnack(snackId);
        return new ResponseEntity(OK);
    }

    @PatchMapping("/snack/{id}")
    public ResponseEntity updateSnack(@PathVariable("id") Integer snackId,
                                  @RequestBody SnackDto snackDto) {
         productService.updateSnack(snackId, snackDto);
        return new ResponseEntity(OK);
    }

    @PostMapping("/snack/create")
    public ResponseEntity createSnack(@ModelAttribute SnackDto snackDto) throws IOException {
         productService.createSnack(snackDto, snackDto.getImage());
        return new ResponseEntity(CREATED);
    }
}