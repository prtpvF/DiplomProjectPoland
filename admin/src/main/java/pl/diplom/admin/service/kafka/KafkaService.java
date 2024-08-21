package pl.diplom.admin.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

        private final KafkaTemplate<String, Object> kafkaTemplate;

        public void sendMessage(String topic, Object message) {
            kafkaTemplate.send(topic, message);
        }

}
