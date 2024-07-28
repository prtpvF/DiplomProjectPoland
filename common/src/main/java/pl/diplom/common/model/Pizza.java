package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pizza")
@Data
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Length(min = 5, max = 30)
    private String name;

    @NotNull
    @Min(0)
    @Max(3000)
    private double cost;

    @OneToOne(mappedBy = "pizza")
    private Recipe recipe;

    @ManyToMany(mappedBy = "pizzas")
    private List<PersonOrder> personOrders = new ArrayList<>();

    private String pathToImage;
}
