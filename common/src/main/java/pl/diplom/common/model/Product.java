package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotBlank
    @Length(min = 2, max = 100)
    private String name;

    @NotNull
    @Min(0)
    @Max(4000)
    private double cost;

    @NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "Admin_Order_id", nullable = false)
    private AdminOrder adminOrder;

}
