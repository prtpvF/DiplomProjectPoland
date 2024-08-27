package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.admin.exception.PersonRoleNotFoundException;
import pl.diplom.common.model.Role;
import pl.diplom.common.repository.RoleRepository;

import javax.management.relation.RoleNotFoundException;

@Service
@RequiredArgsConstructor
public class RoleService {

        private final RoleRepository roleRepository;

        public Role findRoleByName(String roleName) {
            return roleRepository.findByRoleName(roleName)
                    .orElseThrow(() ->  new PersonRoleNotFoundException(
                            "cannot find role with this name"));
        }
}
