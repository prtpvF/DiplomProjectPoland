package pl.diplom.common.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.model.product.Snack;

import java.util.List;


@Repository
public interface SnackRepository extends JpaRepository<Snack, Integer> {

    Page<Snack> findAll(Pageable pageable);

    @Query(value = "SELECT s FROM Snack s WHERE s.name LIKE %:name%")
    List<Snack> findByName(@Param("name") String name);

}
