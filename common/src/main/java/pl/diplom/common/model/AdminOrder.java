package pl.diplom.common.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Admin_Order")
@Data
public class AdminOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        private Person admin;

        @ManyToOne
        private Person consumer;

        private double cost;

        @ManyToOne(cascade = CascadeType.REFRESH)
        @JoinColumn(name = "status_id")
        private Status status;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "adminOrder")
        private List<Product> products = new ArrayList<>();
}
