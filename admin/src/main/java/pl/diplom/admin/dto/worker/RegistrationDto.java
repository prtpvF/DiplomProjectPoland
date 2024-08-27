package pl.diplom.admin.dto.worker;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegistrationDto {

        @NotBlank
        @Length(min=4, max = 20)
        private String firstName;

        @NotBlank
        @Length(min=4, max = 20)
        private String lastName;

        @Email
        @NotBlank
        private String email;

        @NotEmpty
        @Min(18)
        @Max(100)
        private Integer age;

        @Length(min=8, max = 20)
        private String password;

        @NotBlank
        private String username;

        @NotBlank
        private String roleName;
}
