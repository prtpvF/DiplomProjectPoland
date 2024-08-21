package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Ingredient")
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank
        @Length(min=2, max=30)
        private String name;

        @NotNull
        @Min(1)
        @Max(1000)
        private double cost;

        @ManyToMany
        @JoinTable(
                name = "pizza_ingredient",
                joinColumns = {
                        @JoinColumn(name = "pizza_id")
                },
                inverseJoinColumns = {
                        @JoinColumn(name = "ingredient_id")
                }
        )
        private List<Pizza> pizza = new ArrayList<>();

        @Override
        public String toString() {
                return "Ingredient{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", cost=" + cost +
                        '}';
        }
}
