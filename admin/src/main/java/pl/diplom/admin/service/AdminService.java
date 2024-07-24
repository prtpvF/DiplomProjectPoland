package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class AdminService {

        private final PersonRepository personRepository;
        private final IngredientRepository ingredientRepository;
        private final JwtUtil jwtUtil;
        private final ModelMapper modelMapper;

        public HttpStatus createIngredient(IngredientDto ingredientDto){
                ingredientRepository.save(modelMapper.map(ingredientDto, Ingredient.class));
                return HttpStatus.CREATED;
        }

        private Person getAdminFromToken(String token) {
                String username = getUsernameFromToken(token);
                return personRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new PersonNotFoundException("cannot find person with this username"));
        }

        private String getUsernameFromToken(String token) {
                return jwtUtil.validateTokenAndRetrieveClaim(token);
        }
}
