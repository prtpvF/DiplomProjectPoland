package pl.diplom.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.diplom.auth.dto.RegistrationMessageDto;
import pl.diplom.auth.util.MessageTypeEnum;

@Service
@RequiredArgsConstructor
public class RabbitService {
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger;

    public void sendMessageToQueue(String username){
        RegistrationMessageDto message = new RegistrationMessageDto(username, MessageTypeEnum.REGISTRATION);
        rabbitTemplate.convertAndSend("authQueue",message);
        logger.info("message to notification service was successfully send -> %s", message.toString());
    }



}
