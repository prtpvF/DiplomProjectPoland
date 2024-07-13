package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
