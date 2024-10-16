package pl.diplom.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonOrderDto {

    private int person;

    private String status;

    private String address;

    private List<Integer> pizzas = new ArrayList<>();

    private List<Integer> drinks = new ArrayList<>();

    private List<Integer> snacks = new ArrayList<>();

    private Double cost;
}
