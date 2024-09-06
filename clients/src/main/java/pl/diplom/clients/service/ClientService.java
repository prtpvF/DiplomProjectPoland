package pl.diplom.clients.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.clients.dto.PersonOrderDto;
import pl.diplom.clients.exception.*;
import pl.diplom.common.model.*;
import pl.diplom.common.repository.*;
import pl.diplom.security.jwt.JwtUtil;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class ClientService {

        private final JwtUtil jwtUtil;
        private final PersonRepository personRepository;
        private final PersonOrderService personOrderService;
        private final AddressService addressService;
        private final AddressRepository addressRepository;
        private final ModelMapper modelMapper;

        @Transactional
        public HttpStatus createOrder(String token,
                                      PersonOrderDto personOrder) {
                Person person = retrievePersonFromToken(token);
                return personOrderService.createOrder(personOrder, person);
        }

        public HttpStatus deleteOrder(String token, int orderId) {
                Person person = retrievePersonFromToken(token);
              return personOrderService.deleteOrder(orderId, person);
        }

        public Page<PersonOrderDto> findAllPersonOrders(Person person,
                                                        Pageable pageable) {
                return personOrderService.getPersonOrderHistoryAsDtoList(person,
                        pageable);
        }

        public void deleteAccount(String token) {
                Person person = retrievePersonFromToken(token);
                personRepository.delete(person);
        }

        public HttpStatus addAddress(String token, String address) {
                Person person = retrievePersonFromToken(token);
                person = addressService.createAddress(person, address);
                personRepository.save(person);
                return CREATED;
        }

        public HttpStatus deleteAddress(Integer addressId, String token) {
                Person person = retrievePersonFromToken(token);
                return addressService.deleteAddress(addressId, person);
        }

        /**
         * Method retrieves person from token using a username which was retrieved
         * from token in the beginning.
         * Or if a person doesn't found - throws an exception
         *
         * @param token encrypted person data
         * @return founded person
         */
        private Person retrievePersonFromToken(String token) {
                String username = retrieveUsernameFromToken(token);
                return personRepository.findByUsername(username)
                        .orElseThrow(() -> new PersonNotFoundException
                                ("cannot find person with this username"));
        }

        private String retrieveUsernameFromToken(String token) {
                return jwtUtil.validateTokenAndRetrieveClaim(token);
        }

}