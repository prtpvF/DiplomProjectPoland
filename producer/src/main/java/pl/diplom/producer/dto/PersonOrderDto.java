package pl.diplom.producer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonOrderDto {

        private Integer id;

        private String address;

        private PersonDto person;

        private String status;

        private List<PizzaDto> pizzas = new ArrayList<>();

        private List<DrinkDto> drinks = new ArrayList<>();

        private List<SnackDto> snacks = new ArrayList<>();
}
