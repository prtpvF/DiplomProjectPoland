package pl.diplom.common.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {


}
