package pl.diplom.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.exception.IllegalAgeException;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;
import pl.diplom.common.model.enums.PersonRolesEnum;
import pl.diplom.common.repository.RoleRepository;
import pl.diplom.common.util.RoleEnum;

@Component
@RequiredArgsConstructor
public class ObjectMapper {

    private final RoleRepository roleRepository;

    public Person convertFromRegisterDto(RegistrationDto registrationDto) {
        Person person = new Person();
        person.setUsername(registrationDto.getUsername());
        person.setEmail(registrationDto.getEmail());
        person.setPassword(registrationDto.getPassword());
        person.setFirstName(registrationDto.getFirstName());
        person.setLastName(registrationDto.getLastName());
        if(registrationDto.getAge() < 15) {
            throw new IllegalAgeException("you must be older than 14");
        }
        person.setAge(registrationDto.getAge());
        person.setRole(roleRepository.findByRoleName(
                PersonRolesEnum.USER.name()
        ).get());
        return person;
    }
}