package pl.diplom.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonOrderDto {

        private int id;

        private Integer personId;

        private String status;

        private String addressName;

        private LocalDateTime createdAt;

        private List<Integer> pizzaIdList = new ArrayList<>();

        private Double cost;
}
