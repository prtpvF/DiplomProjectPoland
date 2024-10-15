package pl.diplom.publicapi.service;

import pl.diplom.publicapi.dto.StatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Role;
import pl.diplom.common.model.Status;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.repository.RoleRepository;
import pl.diplom.common.repository.StatusRepository;
import pl.diplom.security.jwt.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

        private final StatusRepository statusRepository;
        private final RoleRepository roleRepository;
        private final PersonService personService;
        private final JwtUtil jwtUtil;

        public List<StatusDto> getAllAvailableStatus(String token) {
            List<Status> orderStatuses = new ArrayList<>();
            String username = jwtUtil.validateTokenAndRetrieveClaim(token);
            Person person = personService.findByUsername(username);
            Optional<Role> cook = roleRepository.findByRoleName("COOK");
            Optional<Role> deliveryman = roleRepository.findByRoleName("DELIVERYMAN");
            if (person.getRole().equals(cook)) {
                orderStatuses.add(statusRepository.findByStatus(PersonOrderStatusEnum.IN_PROCESS.name()));
                orderStatuses.add(statusRepository.findByStatus(PersonOrderStatusEnum.READY.name()));
            } else if (person.getRole().equals(deliveryman)) {
                orderStatuses.add(statusRepository.findByStatus(PersonOrderStatusEnum.IN_DELIVER.name()));
                orderStatuses.add(statusRepository.findByStatus(PersonOrderStatusEnum.DELIVERED.name()));
            }
        List<StatusDto> statusDtos = new ArrayList<>();
            for (Status status : orderStatuses) {
                statusDtos.add(convertStatusToDto(status));
            }
            return statusDtos;
        }

        private StatusDto convertStatusToDto(Status status) {
            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(status.getStatus());
            return statusDto;
        }

}
