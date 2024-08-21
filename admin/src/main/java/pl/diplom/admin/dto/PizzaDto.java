package pl.diplom.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PizzaDto {

        @NotBlank
        private String name;

        @NotNull
        private Double cost;
        @NotNull
        private Integer recipeId;

        private String pathToImage;

        private List<Integer> ingredientsIds = new ArrayList<>();

        private List<Integer> personOrderIds = new ArrayList<>();
}
