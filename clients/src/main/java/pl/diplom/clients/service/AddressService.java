package pl.diplom.clients.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.AddressDto;
import pl.diplom.clients.exception.AddressAlreadyExistsException;
import pl.diplom.clients.exception.CannotDeleteAddressException;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.model.enums.PersonOrderStatusEnum;
import pl.diplom.common.repository.AddressRepository;
import pl.diplom.common.repository.PersonOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

        private final AddressRepository addressRepository;
        private final PersonService personService;
    private final PersonOrderRepository personOrderRepository;

    public AddressDto getAddress(String address, Person person) {
            Optional<Address> founded = addressRepository.findByAddressAndPerson(address, person);

            if(founded.isPresent()) {
                return convertToDto(founded.get());
            }
            else {
                throw new EntityNotFoundException("Address not found");
            }
        }

        public List<AddressDto> getAllPersonAddresses(String token) {
            Person person = personService.getPersonFromToken(token);

            List<Address> personAddresses = person.getAddresses();
            List<AddressDto> addressDtos = new ArrayList<>();
           for(Address address : personAddresses) {
               addressDtos.add(convertToDto(address));
           }
            return addressDtos;
        }

        public HttpStatus createAddress(String token, String address) {
            Person person = personService.getPersonFromToken(token);
            isAddressExists(person, address);
            address = convertAddressToLowerCase(address);
            Address addressModel = new Address(person, address);
            person.addAddress(addressModel);
            addressRepository.save(addressModel);
           return OK;
        }

        public HttpStatus deleteAddress(Integer addressId, Person person) {
            isAddressExistsById(addressId);
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find order"));
            isAddressCanBeDeleted(address);
            deleteRelationBetweenAddresses(address);
            addressRepository.deleteById(addressId);
            return OK;
        }

        private void isAddressExists(Person person, String address) {
            List<Address> personAddresses = person.getAddresses();
            for(Address addressModel : personAddresses) {
                if(addressModel.getAddress().equals(address)) {
                    throw new AddressAlreadyExistsException("address already exists");
                }
            }
        }

        private String convertAddressToLowerCase(String address) {
            return address.toLowerCase();
        }

        private void isAddressExistsById(Integer addressId) {
            addressRepository.findById(addressId)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find address"));
        }

        private AddressDto convertToDto(Address address) {
            AddressDto addressDto = new AddressDto();
            addressDto.setAddress(address.getAddress());
            addressDto.setId(address.getId());
            return addressDto;
        }

        private void isAddressCanBeDeleted(Address address) {
            List<PersonOrder> orders = address.getOrders();

            for(PersonOrder order : orders) {
                if (!order.getStatus().equals(PersonOrderStatusEnum.DELIVERED.name())){
                    throw new CannotDeleteAddressException("you can't delete address! You have active orders!");
                }
            }
        }

        private void deleteRelationBetweenAddresses(Address address) {
            List<PersonOrder> orders = address.getOrders();
            for(PersonOrder order : orders) {
                order.setAddress(null);
                personOrderRepository.save(order);
            }
        }
}

