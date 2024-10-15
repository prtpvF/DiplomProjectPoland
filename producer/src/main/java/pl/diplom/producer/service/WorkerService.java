package pl.diplom.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.model.enums.PersonRolesEnum;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.producer.dto.PersonOrderDto;
import pl.diplom.producer.exception.PersonNotFoundException;
import pl.diplom.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class WorkerService {

        private final PersonRepository personRepository;
        private final PersonOrderService personOrderService;
        private final JwtUtil jwtUtil;
        private final PersonOrderRepository personOrderRepository;

        public HttpStatus changeOrderStatus(Integer personOrderId,
                                            String token) {
                Person worker  = getPersonFromToken(token);
                PersonOrder personOrder = personOrderService.getPersonOrderById(personOrderId);

                if(worker.getRole().getRoleName()
                        .equals(PersonRolesEnum.DELIVERYMAN.name())) {
                        changeStatusAsDeliveryman(personOrder);
                }

                else if(worker.getRole().getRoleName()
                        .equals(PersonRolesEnum.COOK.name())) {
                        changeStatusAsCook(personOrder);
                }
                return personOrderService.save(personOrder);
        }

        public Page<PersonOrderDto> getOrderPage(Pageable pageable) {
                Page<PersonOrder> page = personOrderRepository.findAll(pageable);
                Page<PersonOrderDto> dtoPage = page.map(order -> personOrderService.convertToDto(order));
                return dtoPage;
        }

        private static void changeStatusAsCook(PersonOrder personOrder) {
                if(personOrder.getStatus()
                        .equals(PersonOrderStatusEnum.ACCEPTED.name())) {
                        personOrder.setStatus(PersonOrderStatusEnum.IN_PROCESS.name());
                } else if (personOrder.getStatus()
                        .equals(PersonOrderStatusEnum.IN_PROCESS.name())) {
                        personOrder.setStatus(PersonOrderStatusEnum.READY.name());
                }
                else {
                        throw new AccessDeniedException(
                                "You cant change role. " +
                                        "Order is already cooked or delivered or in delivery now");
                }
        }

        private static void changeStatusAsDeliveryman(PersonOrder personOrder) {
                if(personOrder.getStatus()
                        .equals(PersonOrderStatusEnum.READY.name())) {
                        personOrder.setStatus(PersonOrderStatusEnum.IN_DELIVER.name());
                }
                else if(personOrder.getStatus()
                        .equals(PersonOrderStatusEnum.IN_DELIVER.name())) {
                        personOrder.setStatus(PersonOrderStatusEnum.DELIVERED.name());
                }
                else {
                        throw new AccessDeniedException(
                                "you can not change a status. " +
                                        "Order is not ready or already delivered");
                }
        }

        private Person getPersonFromToken(String token) {
                String username = jwtUtil.validateTokenAndRetrieveClaim(token);
                return personRepository.findByUsername(username)
                        .orElseThrow(() -> new PersonNotFoundException("cannot find person"));
        }
}