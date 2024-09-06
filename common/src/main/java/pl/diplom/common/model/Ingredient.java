package pl.diplom.common.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Ingredient")
@Getter
@Setter
@NoArgsConstructor
public class Ingredient extends Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

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

        @OneToMany(mappedBy = "ingredient")
        @JsonManagedReference
        private List<Portion> portions = new ArrayList<>();

        @Override
        public String toString() {
                return "Ingredient{" +
                        "id=" + id +
                        ", name='" + getName() + '\'' +
                        ", cost=" + getCost().toString() +
                        '}';
        }
}
