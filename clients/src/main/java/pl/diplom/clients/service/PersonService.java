package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.diplom.clients.exception.PersonNotFoundException;
import pl.diplom.clients.kafka.KafkaMessageModel;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.security.jwt.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

        private final PersonRepository personRepository;
        private final JwtUtil jwtUtil;
        private final KafkaTemplate<String, KafkaMessageModel> kafkaTemplate;

        public String selectAdminForNotification() {
            List<Person> persons = personRepository.findAllAdmins();
            //todo select random admin
            return persons.get(1).getEmail();
        }

        public Person getPersonFromToken(String token) {
            String username = jwtUtil.validateTokenAndRetrieveClaim(token);
            return personRepository.findByUsername(username)
                    .orElseThrow(() -> new PersonNotFoundException(
                            "cannot find person with username: " + username));
        }
}
