package pl.diplom.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrinkDto {

        private Integer id;

        private String name;

        private String pathToImage;

        private double cost;

        private String taste;

        private String description;

        private Double volume;

        private List<Integer> personOrderIds = new ArrayList<>();
}
