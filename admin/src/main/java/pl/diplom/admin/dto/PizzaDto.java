package pl.diplom.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PizzaDto {

        @NotBlank(message = "cannot be empty")
        private String name;

        private Double cost;

        private String pathToImage;

        private String status;

        private MultipartFile image;

        private List<PortionDto> portions = new ArrayList<>();

        private List<Integer> personOrderIds = new ArrayList<>();
}
