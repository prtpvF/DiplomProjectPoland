package pl.diplom.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.JwtRequestDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.exception.PersonAlreadyExistsException;
import pl.diplom.auth.exception.PersonDoesntExistException;
import pl.diplom.auth.jwt.JwtService;
import pl.diplom.auth.util.ObjectMapper;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    public void registration(RegistrationDto registrationDto) {
        Person person = objectMapper.convertFromRegisterDto(registrationDto);
        isPersonDataValid(person);
        personRepository.save(person);
    }

    private void isPersonDataValid(Person person) {
        isEmailTaken(person);
        isUsernameTaken(person);
    }

    private void isUsernameTaken(Person person) {
        personRepository.findByUsername(person.getUsername())
                .orElseThrow(() -> new PersonAlreadyExistsException("this username is taken"));
    }

    private void isEmailTaken(Person person) {
        personRepository.findByEmail(person.getEmail())
                .orElseThrow(() -> new PersonAlreadyExistsException("this email is taken"));
    }

    private void isPersonExist(String username) {
        personRepository.findByUsername(username)
                .orElseThrow(() -> new PersonDoesntExistException("person with this username doesn't exist"));
    }

    public String generateToken(JwtRequestDto jwtRequestDto) {
        isPersonExist(jwtRequestDto.getUsername());
        return jwtService.generateToken(jwtRequestDto.getUsername());
    }


}
