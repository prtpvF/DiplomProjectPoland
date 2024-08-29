package pl.diplom.common.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.PersonOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pizza")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pizza extends Product{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToMany(mappedBy = "pizza", cascade = CascadeType.REFRESH)
        private List<Ingredient> ingredients = new ArrayList<>();

        private String pathToImage;

        @ManyToMany(mappedBy = "pizzas")
        private List<PersonOrder> personOrders = new ArrayList<>();

}
