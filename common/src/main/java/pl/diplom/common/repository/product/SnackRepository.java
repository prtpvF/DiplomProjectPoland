package pl.diplom.common.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Snack;


@Repository
public interface SnackRepository extends JpaRepository<Snack, Integer> {
}
