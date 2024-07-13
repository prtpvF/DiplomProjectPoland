package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
