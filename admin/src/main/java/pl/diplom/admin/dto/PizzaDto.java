package pl.diplom.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PizzaDto {

        @NotBlank
        private String name;

        @NotNull
        private Double cost;
        @NotNull
        private Integer recipeId;

        private String pathToImage;
}
