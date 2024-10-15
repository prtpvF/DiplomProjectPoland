package pl.diplom.clients.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.diplom.clients.dto.AddressDto;
import pl.diplom.clients.service.AddressService;
import pl.diplom.clients.service.PersonService;
import pl.diplom.common.model.Person;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

        private final AddressService addressService;
        private final PersonService personService;

        @GetMapping("/all")
        public List<AddressDto> allAddresses(@RequestHeader("token") String token) {
            return addressService.getAllPersonAddresses(token);
        }

        @GetMapping("/")
        public AddressDto getAddress(@RequestParam("address") String address,
                                     @RequestHeader("token") String token) {
            Person person = personService.getPersonFromToken(token);
            return addressService.getAddress(address, person);
        }

        @PostMapping("/")
        public HttpStatus createAddress(@RequestParam("address") String address,
                                        @RequestHeader("token") String token) {
            return addressService.createAddress(token, address);
        }

        @DeleteMapping("/{id}")
        public HttpStatus deleteAddress(@RequestHeader("token") String token,
                                    @PathVariable("id") int id) {
            Person person = personService.getPersonFromToken(token);
            return addressService.deleteAddress(id, person);
        }
}
