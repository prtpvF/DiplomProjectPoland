package pl.diplom.clients.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonOrderDtoResponse {

        private int person;

        private String status;

        private String address;

        private List<PizzaDto> pizzas = new ArrayList<>();

        private List<DrinkDto> drinks = new ArrayList<>();

        private List<SnackDto> snacks = new ArrayList<>();

        private Double cost;
}
