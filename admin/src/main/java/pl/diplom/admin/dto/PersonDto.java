package pl.diplom.admin.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.Role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonDto {

    private Integer id;

    private String username;

    private String name;

    private int age;

    @CreationTimestamp
    private Timestamp createdAt;

    private boolean isActive;

    private String email;

    private List<Integer> ordersHistoryIdList = new ArrayList<>();

    private List<Integer> addressIdList = new ArrayList<>();

    private Integer roleId;

}