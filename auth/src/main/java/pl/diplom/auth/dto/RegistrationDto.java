package pl.diplom.auth.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String username;
    private String firstName;
    private String lastName;
    private String name;
    private String password;
    private String email;
    private int age;
}
