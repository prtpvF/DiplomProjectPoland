package pl.diplom.clients.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.diplom.clients.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

        private final AddressService addressService;

        @GetMapping("/all")
        public List<String> allAddresses(@RequestHeader("token") String token) {
            return addressService.getAllPersonAddresses(token);
        }

        @GetMapping("/get")
        public String getAddress(@RequestParam("address") String address,
                                 @RequestHeader("token") String token) {
            return addressService.getAddress(address, token);
        }

        @PostMapping("/new")
        public HttpStatus createAddress(@RequestParam("address") String address,
                                        @RequestHeader("token") String token) {
            return addressService.createAddress(token, address);
        }
}
