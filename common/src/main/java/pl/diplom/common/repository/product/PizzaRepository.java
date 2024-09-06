package pl.diplom.common.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Pizza;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    Optional<Pizza> findPizzaByName(String name);

    Page<Pizza> findAll(Pageable pageable);
}
