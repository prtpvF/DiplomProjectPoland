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
public class SnackDto {

        private Integer id;

        @NotBlank(message = "cannot be empty")
        private String name;

        private String pathToImage;

        private String description;

        @NotNull(message = "cannot be empty")
        private Double cost;

        @NotNull(message = "cannot be empty")
        private Double weight;

        private List<Integer> personOrderIds = new ArrayList<>();
}
