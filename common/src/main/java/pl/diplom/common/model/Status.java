package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Status")
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank()
    @Length(min = 4, max = 30)
    private String status;

    @OneToMany(mappedBy = "status")
    private List<PersonOrder> personOrders = new ArrayList<>();

    @OneToMany(mappedBy = "status")
    private List<AdminOrder> adminsOrders = new ArrayList<>();
}
