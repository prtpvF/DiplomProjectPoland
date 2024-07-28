package pl.diplom.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Recipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @OneToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "pizza_id", referencedColumnName = "id")
        private Pizza pizza;

        @OneToMany(mappedBy = "recipe")
        private List<Ingredient> ingredients = new ArrayList<>();

        public Recipe(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }
}