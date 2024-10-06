package com.example.demo.controller;

import com.example.demo.service.PersonRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-role")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class PersonRoleController {

        private final PersonRoleService personRoleService;

        @GetMapping("/all/names")
        public List<String> getAllRolesNames() {
            return personRoleService.getAllRolesNames();
        }
}
