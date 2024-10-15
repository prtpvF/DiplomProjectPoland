package pl.diplom.publicapi.controller;

import pl.diplom.publicapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/person")
@RequiredArgsConstructor
public class PersonController {

         private final PersonService personService;

         @DeleteMapping("/account")
         public void deleteAccount(@RequestHeader("token") String token) {
             personService.deleteProfile(token);
         }

}
