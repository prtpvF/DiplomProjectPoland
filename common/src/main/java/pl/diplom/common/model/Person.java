package pl.diplom.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank
        @Length(min=4, max = 20)
        private String username;

        @NotBlank
        @Length(min=4, max = 20)
        private String firstName;

        @NotBlank
        @Length(min=4, max = 20)
        private String lastName;

        @Min(value = 15)
        @Max(value = 100)
        private int age;

        @CreationTimestamp
        private Timestamp createdAt;

        private boolean isActive;

        @Email
        @NotBlank
        @Length(min=5, max = 150)
        private String email;

        @UpdateTimestamp
        private Timestamp updatedAt;

        @OneToMany(mappedBy = "person")
        private List<PersonOrder> ordersHistory = new ArrayList<>();

        @OneToMany(mappedBy = "person", cascade = {CascadeType.REMOVE} )
        private List<Address> addresses = new ArrayList<>();

        @ManyToOne(cascade = CascadeType.REFRESH)
        @JoinColumn(name = "role_id", nullable = false)
        private Role role;
        @NotBlank
        private String password;

        public void addAddress(Address address) {
                this.addresses.add(address);
        }
}
