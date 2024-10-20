package pl.diplom.common.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "person_id")
        private Person person;

        private String address;

        @OneToMany(mappedBy = "address", cascade = CascadeType.REFRESH)
        private List<PersonOrder> orders = new ArrayList<>();

        public Address(Person person, String address) {
            this.person = person;
            this.address = address;
        }
}
