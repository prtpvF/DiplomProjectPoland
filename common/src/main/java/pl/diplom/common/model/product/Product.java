package pl.diplom.common.model.product;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@MappedSuperclass
@Getter
@Setter
public abstract class Product {

        @NotBlank
        @Length(min=3, max=30, message = "product name must in 3-30 characters length")
        private String name;

        @NotNull(message = "cost cannot be empty")
        @Min(value = 1, message = "cost cannot be less than 1")
        @Max(value = 4000, message = "cost cannot be bigger than 4000")
        private Double cost;

        private String pathToImage;
}