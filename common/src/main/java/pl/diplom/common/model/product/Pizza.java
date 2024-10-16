package pl.diplom.common.model.product;

import jakarta.persistence.*;
import lombok.*;
import pl.diplom.common.model.Image;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.PersonOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pizza")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pizza extends Product{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToMany(mappedBy = "pizza", cascade = CascadeType.REFRESH)
        private List<Ingredient> ingredients = new ArrayList<>();

        private String pathToImage;

        @ManyToMany(mappedBy = "pizzas")
        //@NotEmpty(message = "Portions list cannot be empty")
        private List<PersonOrder> personOrders = new ArrayList<>();

        private String status;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "pizza")
        //@NotEmpty(message = "image cannot be empty")
        private List<Image> images = new ArrayList<>();

        private Integer previewImageId;
        public void addImage(Image image) {
                image.setPizza(this);
                images.add(image);
        }

}
