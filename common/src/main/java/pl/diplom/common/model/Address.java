package pl.diplom.common.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Person person;

    @ManyToMany(mappedBy = "address")
    private List<Order> orders = new ArrayList<>();
}
