package pl.diplom.common.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.diplom.common.model.PersonOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Drink extends Product{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private Double volume;

        private String pathToImage;

        private String taste;

        @ManyToMany(mappedBy = "drinks")
        private List<PersonOrder> personOrderList = new ArrayList<>();
}
