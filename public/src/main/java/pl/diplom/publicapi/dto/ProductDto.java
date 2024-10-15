package pl.diplom.publicapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

        private int id;

        @NotBlank(message = "cannot be empty")
        private String name;

        private Double cost;

        private String pathToImage;

}
