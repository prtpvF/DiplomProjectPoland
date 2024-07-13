package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
