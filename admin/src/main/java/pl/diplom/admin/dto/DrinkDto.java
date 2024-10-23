package pl.diplom.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrinkDto {

        private Integer id;

        private String name;

        private double cost;

        private String taste;

        private Double volume;

        private Integer quantity;

        private MultipartFile image;

        private List<Integer> personOrderIds = new ArrayList<>();
}
