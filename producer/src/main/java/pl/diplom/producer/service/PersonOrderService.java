package pl.diplom.producer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.producer.dto.PersonOrderDto;
import pl.diplom.security.jwt.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonOrderService {

        private final PersonOrderRepository personOrderRepository;
        //private final Stat

        public PersonOrder getPersonOrderById(Integer orderId) {
               return personOrderRepository.findById(orderId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "cannot find order with this id"));
        }

//        public List<PersonOrderDto> getOrderDtoList() {
//
//            Page<PersonOrder> page = personOrderRepository.findByStatus();
//        }

        public HttpStatus save(PersonOrder personOrder) {
            personOrderRepository.save(personOrder);
            return HttpStatus.OK;
        }
}