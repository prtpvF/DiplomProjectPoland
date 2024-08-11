package pl.diplom.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.exception.IngredientNotFoundException;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Pizza;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.PizzaRepository;
import pl.diplom.security.jwt.JwtUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service was created for admin functional. Here you can find admins method which use in controller,
 * they are marked as public and also can find methods which help you to solve specific problems
 * like converting, validating, retrieving etc.
 * @version 1.0
 * @author  Bohdan Pasichnyk
 */
@Service
@RequiredArgsConstructor
public class AdminService {

        private final PersonRepository personRepository;
        private final IngredientRepository ingredientRepository;
        private final PizzaRepository pizzaRepository;
        private final JwtUtil jwtUtil;
        private final ModelMapper modelMapper;

        public HttpStatus createIngredient(IngredientDto ingredientDto){
                ingredientRepository.save(modelMapper.map(ingredientDto, Ingredient.class));
                return HttpStatus.CREATED;
        }



        @Transactional
        public void createPizza(PizzaDto pizzaDto) {
                Pizza pizza = new Pizza();
                modelMapper.map(pizzaDto, pizza);
                List<Ingredient> ingredients = ingredientRepository.findAllById(pizzaDto.getIngredientsIds());
                ingredients.forEach(ingredient -> ingredient.setPizza(pizza));
                pizza.setIngredients(ingredients);
                pizzaRepository.save(pizza);
        }

        private void setPizzaToIngredient(Ingredient ingredient, Pizza pizza) {
                ingredient.setPizza(pizza);
                ingredientRepository.save(ingredient);
        }

        /**
         * Method finds all requested ingredients what are necessary for recipe.
         * @param ingredientsIdList list of ingredients id what will be found
         * @return list of founded ingredients
         */
        private Set<Ingredient> findAllIngredient(List<Integer> ingredientsIdList) {
                Set<Ingredient> ingredients = new HashSet<>();
                for(Integer id : ingredientsIdList) {
                        Ingredient ingredient = getIngredient(id);
                        ingredients.add(ingredient);
                }
                return ingredients;
        }

        private Ingredient getIngredient(Integer id) {
                return ingredientRepository.findById(id)
                        .orElseThrow(() -> new IngredientNotFoundException("cannot fin ingredient with this id"));

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