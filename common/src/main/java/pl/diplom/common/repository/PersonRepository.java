package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsernameOrEmail(String username, String email);

    @Query(value = "SELECT * FROM Person WHERE role.id=2", nativeQuery = true)
    List<Person> findAllAdmins();

    List<Person> findByRole(Role role);
}