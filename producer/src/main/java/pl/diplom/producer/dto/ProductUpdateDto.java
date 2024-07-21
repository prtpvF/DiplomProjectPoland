package pl.diplom.producer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductUpdateDto {
    @Length(min=2, max=100, message = "message must be in 2-100 characters length")
    private String name;
    @Min(0)
    @Max(4000)
    private Double cost;
    private Integer quantity;
}
