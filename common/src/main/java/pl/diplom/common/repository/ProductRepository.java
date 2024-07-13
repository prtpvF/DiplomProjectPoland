package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
