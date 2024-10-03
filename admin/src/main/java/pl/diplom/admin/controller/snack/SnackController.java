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
    private final SnackRepository snackRepository;

    @GetMapping("/all/snacks")
    public String allSnack(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size) {
        setAuth(model);
        Page<Snack> snackPage = snackRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("snackPage", snackPage);
        return "/snack/all";
    }

    @DeleteMapping("/snack/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus deleteSnack(@PathVariable("id") Integer snackId) {
        return productService.deleteSnack(snackId);
    }

    @PatchMapping("/snack/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public HttpStatus updateSnack(@PathVariable("id") Integer snackId,
                                  @RequestPart SnackDto snackDto) {
        return productService.updateSnack(snackId, snackDto);
    }

    @GetMapping("/snack/{id}")
    public String getSnack(@PathVariable("id") Integer id, Model model) {
        setAuth(model);
        Snack snack = snackRepository.findById(id).orElse(null);
        model.addAttribute("snack", snack);
        return "/snack/page";
    }

    @GetMapping("/update/snack/{id}/page")
    public String updateSnackPage(@PathVariable("id") int id,
                                  Model model) {
        Snack snack = snackRepository.findById(id).orElse(null);
        model.addAttribute("snack", snack);
        return "/snack/update";
    }

    @PostMapping("/snack/create")
    public String createSnack(@ModelAttribute @Valid SnackDto snackDto,
                                  @RequestPart("image") MultipartFile image,
                                  BindingResult bindingResult,
                                  Model model) throws IOException {
        setAuth(model);
        if (bindingResult.hasErrors()) {
            return "/admin/snack/create";
        }
        productService.createSnack(snackDto,image);
        return "redirect:/admin/all/snacks";
    }

    @GetMapping("/snack/create")
    public String createPage(Model model) {
        model.addAttribute("snackDto", new SnackDto());
        return "snack/create";
    }

    private void setAuth(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("userRole", userRole);
    }
}
