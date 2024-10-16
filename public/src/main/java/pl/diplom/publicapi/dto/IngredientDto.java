package pl.diplom.publicapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {

    private Integer id;
    private String name;
    private Double cost;
    private Double weight;
}
