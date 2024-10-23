package pl.diplom.producer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkDto {

        private Integer id;

        private String name;

        private String taste;

        private int quantity;

        private Double cost;

        private Double volume;

}
