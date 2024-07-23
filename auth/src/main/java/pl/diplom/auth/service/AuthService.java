package pl.diplom.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.exception.IncorrectPasswordException;
import pl.diplom.auth.exception.PersonAlreadyExistsException;
import pl.diplom.auth.exception.PersonDoesntExistException;
import pl.diplom.auth.util.ObjectMapper;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

        private final RabbitService rabbitService;
        private final PersonRepository personRepository;
        private final ObjectMapper objectMapper;
        private final JwtUtil jwtUtil;
        private final PasswordEncoder passwordEncoder;;

        public void registration(RegistrationDto registrationDto) {
            Person person = objectMapper.convertFromRegisterDto(registrationDto);
            isPersonDataValid(person);
            personRepository.save(person);
            rabbitService.sendMessageToQueue(registrationDto.getUsername());
        }

        public String login(LoginDto loginDto){
            Person person = findByUsername(loginDto.getUsername());
            doPasswordsMatch(person, loginDto);
            log.info("person has successfully logged in: {}", person.getUsername());
            return jwtUtil.generateToken(person.getUsername());
        }

        private void doPasswordsMatch(Person person, LoginDto loginDto){
            if(!passwordEncoder.matches(loginDto.getPassword(), person.getPassword())){
                throw new IncorrectPasswordException("incorrect password");
            }
        }

        private Person findByUsername(String username){
            return personRepository.findByUsername(username)
                    .orElseThrow(()
                            -> new PersonDoesntExistException("cannot find person with this username"));
        }

        private void isPersonDataValid(Person person){
            personRepository.findByUsernameOrEmail(person.getUsername(),
                    person.getEmail())
                    .ifPresent(ex
                            -> new PersonAlreadyExistsException("person with" +
                            " this username or email already exists"));
        }
}