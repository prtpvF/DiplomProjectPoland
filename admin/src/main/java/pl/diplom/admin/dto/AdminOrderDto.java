package pl.diplom.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrderDto {

        private List<Integer> pizzaIdList = new ArrayList<>();
        private List<Integer> drinkIdList = new ArrayList<>();
        private List<Integer> snackIdList = new ArrayList<>();
        private Integer producerId;

        private Integer adminId;

        private Double price;

        private Integer status;
}
