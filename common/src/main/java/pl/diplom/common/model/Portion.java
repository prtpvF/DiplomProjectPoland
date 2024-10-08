package pl.diplom.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.diplom.common.model.product.Pizza;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient_portion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portion {

        @Id
        @GeneratedValue
        private int id;

        @ManyToOne
        @JoinColumn(name = "ingredient_id", nullable = false) // Убедитесь, что указано правильное имя столбца
        private Ingredient ingredient;

        @ManyToMany
        @JoinTable(
                name = "pizza_portions",
                joinColumns = {
                        @JoinColumn(name = "portion_id")},
                inverseJoinColumns = {@JoinColumn(name = "pizza_id")}
        )
        private List<Pizza> pizza = new ArrayList<>();

        private int weight;

        @Override
        public String toString() {
                return "Portion{" +
                        "id=" + id +
                        ", weight=" + weight +
                        '}';
        }
}
