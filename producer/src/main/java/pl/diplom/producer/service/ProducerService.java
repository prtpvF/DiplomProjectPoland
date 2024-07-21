package pl.diplom.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final PersonRepository producerRepository;

}
