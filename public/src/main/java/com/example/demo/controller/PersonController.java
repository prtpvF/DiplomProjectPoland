package com.example.demo.controller;

import com.example.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public-person")
@RequiredArgsConstructor
public class PersonController {

         private final PersonService personService;

         @DeleteMapping("/account")
         public void deleteAccount(@RequestHeader("token") String token) {
             personService.deleteProfile(token);
         }

}
