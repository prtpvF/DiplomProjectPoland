package pl.diplom.publicapi.service;

import pl.diplom.publicapi.exception.PersonNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.product.PizzaRepository;
import pl.diplom.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class PersonService {

        private final PersonRepository personRepository;
        private final PizzaRepository pizzaRepository;
        private final JwtUtil jwtUtil;
        private final ModelMapper modelMapper;

        public void deleteProfile(String token){
                String username = jwtUtil.validateTokenAndRetrieveClaim(token);
                Person person = personRepository.findByUsername(username)
                        .orElseThrow(() -> new PersonNotFoundException
                                ("cannot find person with this username"));
                personRepository.delete(person);
        }

        public Person findByUsername(String username){
                Person person = personRepository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException("cannot find person with this username"));
                return person;
        }

}
