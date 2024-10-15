package pl.diplom.clients.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.AddressDto;
import pl.diplom.clients.exception.AddressAlreadyExistsException;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.AddressRepository;

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
            addressRepository.deleteById(addressId);
            return OK;
        }

        private void isAddressExists(Person person, String address) {
            addressRepository.findByAddressAndPerson(address, person)
                    .ifPresent(ex -> new AddressAlreadyExistsException("address already exists"));
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
}
