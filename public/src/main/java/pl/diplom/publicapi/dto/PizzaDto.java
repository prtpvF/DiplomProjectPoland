package pl.diplom.publicapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PizzaDto {

        private int id;

        @NotBlank(message = "cannot be empty")
        private String name;

        private Double cost;

        private String pathToImage;

        private String description;

        private List<IngredientDto> ingredients = new ArrayList<>();

        private String status;

        private List<Integer> personOrderIds = new ArrayList<>();
}
