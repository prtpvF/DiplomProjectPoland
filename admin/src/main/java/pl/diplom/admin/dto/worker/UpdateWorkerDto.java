package pl.diplom.admin.dto.worker;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateWorkerDto {

        private String firstName;

        private String lastName;

        private String email;

        private Integer age;

        private String password;

        private String username;

        private String roleName;
}
