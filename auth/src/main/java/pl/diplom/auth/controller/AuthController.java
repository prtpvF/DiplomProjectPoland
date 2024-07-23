package pl.diplom.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/registration")
        public HttpStatus registration(@RequestBody RegistrationDto registrationDto) {
            authService.registration(registrationDto);
            return HttpStatus.CREATED;
        }

        @PostMapping("/login")
        public String login(@RequestBody LoginDto loginDto) {
            return authService.login(loginDto);
        }
}
