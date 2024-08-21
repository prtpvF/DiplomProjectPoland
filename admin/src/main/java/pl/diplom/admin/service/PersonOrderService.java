package pl.diplom.admin.service;

import com.google.common.base.Enums;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.AddressDto;
import pl.diplom.admin.dto.PersonOrderDto;
import pl.diplom.admin.exception.IllegalOrderStatusException;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.repository.PersonOrderRepository;
import pl.diplom.common.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class PersonOrderService {

        private final PersonOrderRepository personOrderRepository;
        private final AddressService addressService;
        private final PersonRepository personRepository;
        private final ModelMapper modelMapper;

        public PersonOrder getPersonOrderById(Integer id) {
            return personOrderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find order with this id"));
        }
        public HttpStatus updateAddressForPersonOrder(Integer id,
                                                      String addressName) {
            PersonOrder orderNeedToBeUpdated = getPersonOrderById(id);
            orderNeedToBeUpdated.setId(orderNeedToBeUpdated.getId());
            Address address = addressService
                    .findOrCreateAddress(
                            addressName,
                            orderNeedToBeUpdated.getPerson());

            orderNeedToBeUpdated.setAddress(
                address);
            personOrderRepository.save(orderNeedToBeUpdated);
            return OK;
        }

        public HttpStatus updatePersonOrderStatus(String statusName,
                                                  Integer orderNeedToBeUpdatedId) {
            PersonOrder order = getPersonOrderById(orderNeedToBeUpdatedId);
            if(String.valueOf(Enums.getIfPresent(PersonOrderStatusEnum.class, statusName)).isBlank()) {
                throw new IllegalOrderStatusException(
                        "cannot fin order status wot this name");
            }
            order.setStatus(statusName);
            personOrderRepository.save(order);
            return OK;
        }

        public HttpStatus removeOrderFromPersonHistory(Integer id) {
            PersonOrder personOrder = getPersonOrderById(id);
            personOrderRepository.delete(personOrder);
            return OK;
        }

        private Address convertAddressFromDto(AddressDto dto) {
            Address address = new Address();
            modelMapper.map(dto, address);

            address.setPerson(findPersonById(dto.getOwnerId()));
            address.setOrders(findAllPersonOrderById(dto.getOrderIdList()));
            return address;
        }

        private Person findPersonById(Integer personId) {
            return personRepository.findById(personId)
                    .orElseThrow(() -> new PersonNotFoundException(
                            "cannot find person with this id"
                    ));
        }

        private List<PersonOrder> findAllPersonOrderById(List<Integer> ids) {
            List<PersonOrder> orders =  personOrderRepository.findAllById(ids);
            if (orders.isEmpty()) {
                return new ArrayList<>();
            }
            return orders;
        }
}