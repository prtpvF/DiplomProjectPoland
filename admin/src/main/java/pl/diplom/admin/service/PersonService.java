package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.PersonDto;
import pl.diplom.admin.dto.worker.RegistrationDto;
import pl.diplom.admin.dto.worker.UpdateWorkerDto;
import pl.diplom.admin.exception.IllegalPersonDataException;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;
import pl.diplom.common.model.enums.PersonRolesEnum;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.security.jwt.JwtUtil;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Service stores method which save,delete and update new admins
 */
@Service
@RequiredArgsConstructor
public class PersonService {

        private final PersonRepository personRepository;
        private final ModelMapper modelMapper;
        private final RoleService roleService;
        private final JwtUtil jwtUtil;
        private final PasswordEncoder passwordEncoder;

        private final String ADMIN_ROLE = "ADMIN";

        public PersonDto findByUsername(String username) {
            Person person = personRepository.findByUsername(username)
                    .orElseThrow(() -> new PersonNotFoundException(username));
            return convertPersonTpoDto(person);
        }

        public List<PersonDto> getAllWorkers() {
            List<Person> persons = personRepository.findAll();
            List<PersonDto> personDtos = new ArrayList<>();

            for (Person person : persons) {
                if(!person.getRole().getRoleName().equals("USER")
                        && !person.getRole().getRoleName().equals("ADMIN"))
                personDtos.add(convertPersonTpoDto(person));
            }
            return personDtos;
        }

        public HttpStatus createNewWorker(RegistrationDto registrationDto) {
            Person person = mapAdttionalFieldToperson(registrationDto);
            isUsernameAndEmailTaken(registrationDto.getUsername(),
                                    registrationDto.getEmail());
            person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            person.setRole(roleService.findRoleByName(
                    registrationDto.getRoleName()));
            personRepository.save(person);
            return CREATED;
        }

        public HttpStatus deleterWorker(Integer workerId) {
            Person workerNeedToBeDeleted = findPersonById(workerId);
            personRepository.delete(workerNeedToBeDeleted);
            return OK;
        }

        public HttpStatus updateWorker(Integer workerIdNeedToBeUpdated,
                                       UpdateWorkerDto updateWorkerDto) {
            Person workerNeedToBeUpdated = findPersonById(workerIdNeedToBeUpdated);
            modelMapper.map(updateWorkerDto, workerNeedToBeUpdated);
            workerNeedToBeUpdated.setId(workerNeedToBeUpdated.getId());
            workerNeedToBeUpdated.setPassword(passwordEncoder
                    .encode(updateWorkerDto
                            .getPassword()));
            workerNeedToBeUpdated.setRole(roleService.findRoleByName(
                    updateWorkerDto.getRoleName()
            ));
           if(workerNeedToBeUpdated.getUsername().equals(updateWorkerDto.getUsername())) {
               workerNeedToBeUpdated.setUsername(updateWorkerDto.getUsername());
           }
           else {
               boolean isUsernameTaken = isUsernameTaken(updateWorkerDto.getUsername());
               if(!isUsernameTaken) {
                   workerNeedToBeUpdated.setUsername(updateWorkerDto.getUsername());
               }
               else {
                   throw new IllegalPersonDataException("username is taken");
               }
           }

           if(workerNeedToBeUpdated.getEmail().equals(updateWorkerDto.getEmail())) {
               workerNeedToBeUpdated.setEmail(updateWorkerDto.getEmail());
           }
           else {
               boolean isEmailTaken = isEmailTaken(updateWorkerDto.getEmail());

               if(!isEmailTaken) {
                   workerNeedToBeUpdated.setEmail(updateWorkerDto.getEmail());
               }

               else {
                   throw new IllegalPersonDataException("email is taken");
               }
           }
            personRepository.save(workerNeedToBeUpdated);
            return CREATED;
        }

        public boolean isAdmin(String token) {
            Person person = getPersonByToken(token);
            if(person.getRole()
                    .getRoleName()
                    .equals(PersonRolesEnum.ADMIN.name())) {
                return true;
            }
            throw new IllegalPersonDataException("you are not an admin!");
        }

        public Person findPersonById(Integer id) {
            return personRepository.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException(
                            "cannot find person with this id"));
        }

        private Person getPersonByToken(String token) {
            Person person = personRepository.findByUsername(
                    jwtUtil.validateTokenAndRetrieveClaim(token))
                    .orElseThrow(() -> new PersonNotFoundException(
                            "cannot find person with this username"
                    ));
            return  person;
        }

        private void isUsernameAndEmailTaken(String username,
                                             String email) {
            personRepository.findByUsernameOrEmail(username, email)
                    .ifPresent(s -> {throw new IllegalPersonDataException(
                            "person with this credentials already exists");
                    });
        }

        private Person mapAdttionalFieldToperson(RegistrationDto registrationDto) {
            Person person = new Person();
            person.setUsername(registrationDto.getUsername());
            person.setEmail(registrationDto.getEmail());
            person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            person.setRole(roleService.findRoleByName(
                    registrationDto.getRoleName()
            ));
            person.setActive(true);
            person.setAge(registrationDto.getAge());
            person.setFirstName(registrationDto.getFirstName());
            person.setLastName(registrationDto.getLastName());
            return person;
        }

        private PersonDto convertPersonTpoDto(Person person) {
            PersonDto personDto = new PersonDto();
            personDto.setId(person.getId());
            personDto.setUsername(person.getUsername());
            personDto.setEmail(person.getEmail());
            personDto.setRole(person.getRole().getRoleName());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setActive(person.isActive());
            personDto.setAge(person.getAge());
            return personDto;
        }

        private boolean isUsernameTaken(String username) {
            Optional<Person> person = personRepository.findByUsername(username);
            return person.isPresent();
        }

        private boolean isEmailTaken(String email) {
            Optional<Person> person = personRepository.findByEmail(email);
            return person.isPresent();
        }


}