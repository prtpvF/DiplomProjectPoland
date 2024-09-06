package pl.diplom.clients.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.exception.AddressAlreadyExistsException;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.AddressRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

        private final AddressRepository addressRepository;

        public Address findOrCreateAddress(String address, Person person) {
            Optional<Address> founded = addressRepository.findByAddressAndPerson(address, person);
            if (founded.isPresent()) {
                return founded.get();
            } else {
                return addressRepository.save(new Address(person, address));
            }
        }

        public Person createAddress(Person person, String address) {
            isAddressExists(person, address);
            address = convertAddressToLowerCase(address);
            Address addressModel = new Address(person, address);
            person.addAddress(addressModel);
            addressRepository.save(addressModel);
           return person;
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
}
