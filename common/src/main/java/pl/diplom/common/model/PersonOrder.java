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
import pl.diplom.common.model.product.Snack;

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
        @JoinColumn(name = "person_id")
        private Person person;

        private String status;

        @ManyToOne(cascade = CascadeType.REFRESH)
        @JoinColumn(name = "address_id")
        private Address address;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @ManyToMany(cascade = CascadeType.REFRESH)
        @JoinTable(name = "Order_Pizza",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "pizza_id")})
        private List<Pizza> pizzas = new ArrayList<>();

        @ManyToMany(cascade = CascadeType.REFRESH)
        @JoinTable(name = "Order_Drink",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "drink_id")})
        private List<Drink> drinks = new ArrayList<>();

        @ManyToMany(cascade = CascadeType.REFRESH)
        @JoinTable(name = "Order_Snack",
                joinColumns = {@JoinColumn(name = "id")},
                inverseJoinColumns = {@JoinColumn(name = "snack_id")})
        private List<Snack> snacks = new ArrayList<>();

        @NotNull
        private double cost;
}
