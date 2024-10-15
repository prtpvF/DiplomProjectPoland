package pl.diplom.common.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    Page<Drink> findAll(Pageable pageable);

    @Query(value = "SELECT d FROM Drink d WHERE d.name LIKE %:name%")
    List<Drink> findByName(@Param("name") String name);

}
