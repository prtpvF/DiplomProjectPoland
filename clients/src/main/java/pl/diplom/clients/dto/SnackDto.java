package pl.diplom.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SnackDto {

        private Integer id;

        private String name;

        private String pathToImage;

        private String description;

        private Double cost;

        private Double weight;

        private List<Integer> personOrderIds = new ArrayList<>();
}
