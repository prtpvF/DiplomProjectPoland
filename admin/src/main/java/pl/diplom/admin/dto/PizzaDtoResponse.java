package pl.diplom.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PizzaDtoResponse {

    @NotBlank(message = "cannot be empty")
    private String name;

    private Integer id;

    private Double cost;

    private String pathToImage;

    private String status;

    private MultipartFile image;

    private List<IngredientDto> ingredients = new ArrayList<>();

    private List<Integer> personOrderIds = new ArrayList<>();
}
