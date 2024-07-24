package pl.diplom.admin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IngredientDto {

        @NotBlank(message = "ingredient name cannot be blank")
        private String name;

        @NotNull(message = "cost cannot be empty")
        @Min(value = 1, message = "cost cannot be less than 1")
        @Max(value = 4000, message = "cost cannot be bigger than 4000")
        private Double cost;
}
