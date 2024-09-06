package pl.diplom.common.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;


@Repository
public interface SnackRepository extends JpaRepository<Snack, Integer> {

    Page<Snack> findAll(Pageable pageable);
}
