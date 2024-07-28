package pl.diplom.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity registration(@Valid @RequestBody RegistrationDto registrationDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        authService.registration(registrationDto);
        return new ResponseEntity("success", HttpStatus.CREATED);
    }


    @PostMapping("/login")
        public String login(@RequestBody LoginDto loginDto) {
            return authService.login(loginDto);
        }

        @GetMapping("/logout")
        public void logout(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
            authService.logout(request, response, token);
        }
}
