package pl.diplom.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.service.AuthService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @GetMapping("/reg")
        public String showRegistrationForm(Model model) {
            return "/auth/reg";
        }

        @GetMapping("/login")
        public String showLoginForm(Model model) {
            return "/auth/login";
        }

        @PostMapping("/registration")
        public HttpStatus registration(@Valid @RequestBody RegistrationDto registrationDto) {
            authService.registration(registrationDto);
           return HttpStatus.CREATED;
        }

        @PostMapping("/login")
        public Map<String, String> login(@RequestBody LoginDto loginDto) {
            return authService.login(loginDto);
        }

        @GetMapping("/logout")
        public void logout(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
            authService.logout(request, response, token);
        }
}
