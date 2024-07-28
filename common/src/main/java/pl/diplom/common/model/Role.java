package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<Person> persons = new ArrayList<>();

    public Role(String roleName){
        this.roleName = roleName;
    }

    public Role(String roleName, int id) {
        this.roleName = roleName;
        this.id = id;
    }
}
