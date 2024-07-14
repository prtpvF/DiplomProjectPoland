package pl.diplom.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.diplom.auth.util.MessageTypeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationMessageDto {
    private String username;
    private MessageTypeEnum messageType;
}
