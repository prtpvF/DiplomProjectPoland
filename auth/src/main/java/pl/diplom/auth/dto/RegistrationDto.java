package pl.diplom.auth.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String username;
    private String name;
    private String password;
    private int age;
    private boolean isConsumer;
}
