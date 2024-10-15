package pl.diplom.publicapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Role;
import pl.diplom.common.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonRoleService {

        private final RoleRepository roleRepository;

        public List<String> getAllRolesNames() {
            List<Role> roles = roleRepository.findAll();
            List<String> roleNames = new ArrayList<>();

            for (Role role : roles) {
                roleNames.add(role.getRoleName());
            }
            return roleNames;
        }
}
