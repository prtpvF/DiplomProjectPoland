package pl.diplom.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Status;
import pl.diplom.common.repository.StatusRepository;
import pl.diplom.producer.exception.OrderStatusNotFoundException;

@Service
@RequiredArgsConstructor
public class StatusService {

        private final StatusRepository statusRepository;

        public Status getStatusByName(String statusName) {
            return statusRepository.findByStatus(statusName)
                    .orElseThrow(() -> new OrderStatusNotFoundException("cannot find order status with this id"));
        }
}
