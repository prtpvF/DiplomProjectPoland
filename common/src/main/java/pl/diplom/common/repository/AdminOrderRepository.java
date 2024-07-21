package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.AdminOrder;

@Repository
public interface AdminOrderRepository extends JpaRepository<AdminOrder, Integer> {
}
