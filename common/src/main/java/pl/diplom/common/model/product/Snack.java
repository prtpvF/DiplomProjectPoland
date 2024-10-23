package pl.diplom.common.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.diplom.common.model.Image;
import pl.diplom.common.model.PersonOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Snack extends Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private double weight;

        private String pathToImage;

        private int quantity;

        @ManyToMany(mappedBy = "snacks")
        private List<PersonOrder> personOrderList = new ArrayList<>();

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "snack")
        private List<Image> images = new ArrayList<>();

        private Integer previewImageId;
        public void addImage(Image image) {
                image.setSnack(this);
                images.add(image);
        }
}
