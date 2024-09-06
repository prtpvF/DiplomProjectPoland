package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Portion;

import java.util.Optional;

@Repository
public interface PortionRepository extends JpaRepository<Portion, Integer> {

        Optional<Portion> findPortionByIngredientAndWeight(Ingredient ingredient,
                                                           Integer weight);
}
