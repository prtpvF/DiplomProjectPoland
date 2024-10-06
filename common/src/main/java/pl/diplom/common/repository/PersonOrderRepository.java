package pl.diplom.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.Status;

@Repository
public interface PersonOrderRepository extends JpaRepository<PersonOrder, Integer> {

        Page<PersonOrder> findByPerson(Person person, Pageable pageable);
        Page<PersonOrder> findByStatus(Status status, Pageable pageable);
}
