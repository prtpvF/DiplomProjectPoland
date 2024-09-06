package pl.diplom.common.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Drink;
import pl.diplom.common.model.product.Pizza;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    Page<Drink> findAll(Pageable pageable);
}
