package pl.diplom.producer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.repository.PersonOrderRepository;

@Service
@RequiredArgsConstructor
public class PersonOrderService {

        private final PersonOrderRepository personOrderRepository;

        public PersonOrder getPersonOrderById(Integer orderId) {
               return personOrderRepository.findById(orderId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find order with this id"));
        }

        public HttpStatus save(PersonOrder personOrder) {
            personOrderRepository.save(personOrder);
            return HttpStatus.OK;
        }
}