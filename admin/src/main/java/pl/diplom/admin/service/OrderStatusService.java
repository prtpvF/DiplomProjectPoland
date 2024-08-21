package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Status;
import pl.diplom.common.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

        private final StatusRepository statusRepository;

        public Status findStatusById(Integer id) {
            return statusRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find status with id: " + id));
        }

}
