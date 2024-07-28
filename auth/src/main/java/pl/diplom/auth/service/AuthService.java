package pl.diplom.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
import pl.diplom.auth.exception.IncorrectPasswordException;
import pl.diplom.auth.exception.PersonAlreadyExistsException;
import pl.diplom.auth.exception.PersonDoesntExistException;
import pl.diplom.auth.util.ObjectMapper;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;
import pl.diplom.common.model.enums.PersonRolesEnum;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.RoleRepository;
import pl.diplom.security.jwt.JwtUtil;
import pl.diplom.security.service.PersonDetailsService;
import pl.diplom.security.util.PersonDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

        private final PersonDetailsService personDetailsService;
        private final PersonRepository personRepository;
        private final RoleRepository roleRepository;
        private final ObjectMapper objectMapper;
        private final JwtUtil jwtUtil;
        private final PasswordEncoder passwordEncoder;

        public void registration(RegistrationDto registrationDto) {
            checkPersonExists(registrationDto.getUsername(), registrationDto.getEmail());
            Person person = objectMapper.convertFromRegisterDto(registrationDto);
            preparePersonForRegistration(person, registrationDto.isConsumer());
            personRepository.save(person);
            log.info("New person has registered: {}", person);
        }

        public String login(LoginDto logInDto) {
            Person person = personRepository.findByUsername(logInDto.getUsername())
                    .filter(p -> passwordEncoder.matches(logInDto.getPassword(), p.getPassword()))
                    .orElseThrow(() -> new PersonDoesntExistException("Person with these credentials not found"));
            authPerson(person.getUsername());
            return createJwtToken(person.getUsername());
        }

        private void authPerson(String username){
            PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(personDetails, null, personDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        private void checkPersonExists(String username, String email) {
            personRepository.findByUsernameOrEmail(username, email).ifPresent(p -> {
                throw new PersonAlreadyExistsException("Person with this credentials already exists");
            });
        }

        private void preparePersonForRegistration(Person person, boolean consumer) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRole(getPersonRole(consumer));
        }

        private Role getPersonRole(boolean consumer) {
            String roleName = consumer ? PersonRolesEnum.CONSUMER.name() : PersonRolesEnum.USER.name();
            return roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        }


        private String createJwtToken(String username) {
            return jwtUtil.generateToken(username);
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

        public void logout(HttpServletRequest request, HttpServletResponse response, String token) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                jwtUtil.removeToken(getUsernameFromToken(token));
            }
        }
        private String getUsernameFromToken(String token) {
            return jwtUtil.validateTokenAndRetrieveClaim(token);
        }
}