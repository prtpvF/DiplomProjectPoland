package pl.diplom.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.exception.PersonAlreadyExistsException;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;
import util.ObjectMapper;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;

    public void registration(RegistrationDto registrationDto){
        Person person = objectMapper.convertFromRegisterDto(registrationDto);
        isPersonExist(person);
        personRepository.save(person);
    }
    private void isPersonExist(Person person){
        personRepository.findByUsername(person.getUsername())
                .orElseThrow(() -> new PersonAlreadyExistsException("this username is taken"));
    }



}
