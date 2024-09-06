package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.ProductDto;
import pl.diplom.clients.kafka.KafkaMessageModel;
import pl.diplom.common.model.product.Product;

@Service
@RequiredArgsConstructor
public class NotificationService {

        private final KafkaTemplate<String, KafkaMessageModel> kafkaTemplate;
        private PersonService personService;

        @Value("${spring.kafka.topic-product.name}")
        private String topicName;

        //todo test
        public void sendNotificationToAdmin(Product product) {
                KafkaMessageModel message = new KafkaMessageModel();
                String emailTo = personService.selectAdminForNotification();
                String body = "product with name: " + product.getName() +
                         "almost gone. Quantity: " + product.getQuantity();
                message.setBody(body);
                message.setSentToEmail(emailTo);
                kafkaTemplate.send(topicName, message);
        }
}
