package pl.diplom.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.diplom.auth.dto.JwtRequestDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.jwt.JwtService;
import pl.diplom.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public HttpStatus registration(@RequestBody RegistrationDto registrationDto){
        authService.registration(registrationDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("/token")
    public String getToken(JwtRequestDto jwtRequestDto){
        return authService.generateToken(jwtRequestDto);
    }
}
