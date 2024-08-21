package pl.diplom.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressDto {

        private Integer id;

        private Integer ownerId;

        private String address;

        private List<Integer> orderIdList = new ArrayList<>();
}
