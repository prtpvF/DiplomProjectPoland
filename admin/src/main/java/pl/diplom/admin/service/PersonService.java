package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.worker.RegistrationDto;
import pl.diplom.admin.dto.worker.UpdateWorkerDto;
import pl.diplom.admin.exception.IllegalPersonDataException;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;

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
        private final PasswordEncoder passwordEncoder;

        public HttpStatus createNewWorker(RegistrationDto registrationDto) {
            Person person = modelMapper.map(registrationDto, Person.class);
            isUsernameAndEmailTaken(registrationDto.getUsername(),
                                    registrationDto.getEmail());
            person.setPassword(passwordEncoder.encode(person.getPassword()));
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
            isUsernameAndEmailTaken(updateWorkerDto.getUsername(),
                                    updateWorkerDto.getEmail());
            personRepository.save(workerNeedToBeUpdated);
            return CREATED;
        }

        private Person findPersonById(Integer id) {
            return personRepository.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException(
                            "cannot find person with this id"));
        }

        private void isUsernameAndEmailTaken(String username,
                                             String email) {
            personRepository.findByUsernameOrEmail(username, email)
                    .ifPresent(s -> {throw new IllegalPersonDataException(
                            "person with this credentials already exists");
                    });
        }
}