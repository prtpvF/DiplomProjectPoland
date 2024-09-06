package pl.diplom.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortionDto {

        private Integer id;

        private Integer ingredientId;

        private String ingredientName;

        private List<Integer> pizzaIds = new ArrayList<>();

        private Integer weight;
}