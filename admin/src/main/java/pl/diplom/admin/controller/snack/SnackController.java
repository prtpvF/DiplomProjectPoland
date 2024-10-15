package pl.diplom.admin.controller.snack;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.diplom.admin.dto.SnackDto;
import pl.diplom.admin.service.ProductService;
import pl.diplom.common.model.product.Snack;
import pl.diplom.common.repository.product.SnackRepository;

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
                                  @RequestPart SnackDto snackDto) {
        return productService.updateSnack(snackId, snackDto);
    }

    @PostMapping("/snack/create")
    public HttpStatus createSnack(@ModelAttribute @Valid SnackDto snackDto,
                                  @RequestPart("image") MultipartFile image) throws IOException {
        return productService.createSnack(snackDto,image);
    }
}