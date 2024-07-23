package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Ingredient")
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @NotBlank
    @Length(min=2, max=30)
    private String name;

    @NotNull
    @Min(1)
    @Max(1000)
    private double cost;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
