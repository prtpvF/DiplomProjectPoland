package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Status;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

        Optional<Status> findByStatus(String name);
}

