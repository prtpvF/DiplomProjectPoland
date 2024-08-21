package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person_Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "person_id", nullable = false)
        private Person person;

        private String status;

        @ManyToOne(cascade = CascadeType.REFRESH)
        @JoinColumn(name = "address_id", nullable = false)
        private Address address;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @ManyToMany
        @JoinTable(name = "Order_Pizza",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "product_id")})
        private List<Pizza> pizzas = new ArrayList<>();

        @ManyToMany
        @JoinTable(name = "Order_Drink",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "drink_id")})
        private List<Drink> drinks = new ArrayList<>();

        @ManyToMany
        @JoinTable(name = "Order_Product",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "product_id")})
        private List<Pizza> snacks = new ArrayList<>();

        @NotNull
        @Min(10)
        @Max(100000)
        private double cost;
}
