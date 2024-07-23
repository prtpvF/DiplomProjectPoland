package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.PersonOrder;

@Repository
public interface OrderRepository extends JpaRepository<PersonOrder, Integer> {
}
