package pl.diplom.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;
import pl.diplom.common.util.RoleEnum;

@Component
@RequiredArgsConstructor
public class ObjectMapper {

    private final PasswordEncoder passwordEncoder;

    public Person convertFromRegisterDto(RegistrationDto registrationDto) {
        Person person = new Person();
        person.setName(registrationDto.getName());
        person.setUsername(registrationDto.getUsername());
        person.setEmail(registrationDto.getEmail());
        person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        person.setAge(registrationDto.getAge());
        person.setRole(definePersonRole(registrationDto.isConsumer()));
        return person;
    }

    private Role definePersonRole(boolean isConsumer){
        if(isConsumer){
            return new Role(RoleEnum.CONSUMER.name());
        }
        return new Role(RoleEnum.USER.name());
    }
}
