package pl.diplom.clients.configuration;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import pl.diplom.clients.kafka.KafkaMessageModel;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

        @Bean
        public NewTopic newAuthTopic() {
            return new NewTopic("product-topic",
                    1,
                    (short) 1);
        }

        @Bean
        public NewTopic newVerifyTopic() {
            return new NewTopic("order-topic",
                    1,
                    (short) 1);
        }

        @Bean
        public ProducerFactory<String, KafkaMessageModel> producerFactory() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                return new DefaultKafkaProducerFactory<>(props);
        }

        @Bean
        public KafkaTemplate<String, KafkaMessageModel> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }
}