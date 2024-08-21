package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pizza")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank
        @Length(min = 5, max = 30)
        private String name;

        @NotNull
        @Min(0)
        @Max(3000)
        private double cost;

        @ManyToMany(mappedBy = "pizza", cascade = CascadeType.REFRESH)
        private List<Ingredient> ingredients = new ArrayList<>();

        @ManyToMany(mappedBy = "pizzas")
        private List<PersonOrder> personOrders = new ArrayList<>();

        private String pathToImage;
}
