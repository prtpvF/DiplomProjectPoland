package pl.diplom.clients.dto;

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

        private Integer id;

        @NotBlank
        private String name;

        private Double cost;

        private String pathToImage;

        private String description;

        private String status;

        private List<PortionDto> portions = new ArrayList<>();

        private List<Integer> personOrderIds = new ArrayList<>();
}
