package pl.diplom.publicapi.controller;

import pl.diplom.publicapi.service.PersonRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/role")
public class PersonRoleController {

        private final PersonRoleService personRoleService;

        @GetMapping("/all/names")
        public List<String> getAllRolesNames() {
            return personRoleService.getAllRolesNames();
        }
}
