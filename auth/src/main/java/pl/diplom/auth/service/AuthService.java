package pl.diplom.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.LoginDto;
import pl.diplom.auth.dto.RegistrationDto;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            person.setRole(getPersonRole(registrationDto.isConsumer()));
            //preparePersonForRegistration(person, registrationDto.isConsumer());
            personRepository.save(person);
            log.info("New person has registered: {}", person);
        }

        public Map<String, String> login(LoginDto logInDto) {
        Person person = findByUsername(logInDto.getUsername());
        boolean isPasswordMatch = passwordEncoder.matches(logInDto.getPassword(), person.getPassword());
            if (isPasswordMatch) {
                authPerson(person.getUsername());
                String token = createJwtToken(person.getUsername());
                Map<String, String> data = new HashMap<>();
                data.put("access-token", token);
                return data;
            }
            else {
                throw new IllegalArgumentException("credentials is not correct!");
            }
        }

        /**
         * Method authorizes person using username
         * and adds authorized person into security context holder.
         * @param username - username of person which will be authorized
         */
        private void authPerson(String username){
            PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(personDetails,
                            null,
                                      personDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        private void checkPersonExists(String username, String email) {
            personRepository.findByUsernameOrEmail(username, email).ifPresent(p -> {
                throw new PersonAlreadyExistsException("Person with this credentials already exists");
            });
        }

        /**
         * Method prepares person fo saving into db.
         * In this method a person get role and encrypted password
         * @param person model of person what will be preapred for saving
         * @param consumer flag of "is person a consumer ir not"
         */
        private void preparePersonForRegistration(Person person, boolean consumer) {
            person.setPassword(person.getPassword());
            person.setRole(getPersonRole(consumer));
        }

        /**
         * Method checks is param true or not. If it's true, person role = consumer
         * else = user
         * @param consumer  flag of person role (true - consumer, false - user)
         * @return Role model
         */
        private Role getPersonRole(boolean consumer) {
            String roleName = consumer ? PersonRolesEnum.CONSUMER.name() : PersonRolesEnum.USER.name();
            return roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        }


        private String createJwtToken(String username) {
            return jwtUtil.generateToken(username);
        }

        private Person findByUsername(String username){
            return personRepository.findByUsername(username)
                    .orElseThrow(()
                            -> new PersonDoesntExistException("cannot find person with this username"));
        }

        public void logout(HttpServletRequest request, HttpServletResponse response, String token) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                jwtUtil.removeToken(getUsernameFromToken(token));
            }
        }

        /**
         * Method retrieves username from token.
         * @param token encrypted person data
         * @return founded username
         */
        private String getUsernameFromToken(String token) {
            return jwtUtil.validateTokenAndRetrieveClaim(token);
        }
}