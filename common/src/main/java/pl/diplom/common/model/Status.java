package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Status")
@Getter
@Setter
@NoArgsConstructor
public class Status {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank()
        @Length(min = 4, max = 30)
        private String status;

        @OneToMany(mappedBy = "status")
        private List<PersonOrder> personOrders = new ArrayList<>();

        public Status(String status) {
            this.status = status;
        }
}
