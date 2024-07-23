package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.diplom.common.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
