package pl.diplom.clients.kafka;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaMessageModel {

       private String topic;
       private String sentToEmail;
       private String body;
}
