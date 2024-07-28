package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.admin.exception.RecipeNotFoundException;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.Pizza;
import pl.diplom.common.model.Recipe;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.PizzaRepository;
import pl.diplom.common.repository.RecipeRepository;
import pl.diplom.security.jwt.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

        private final PersonRepository personRepository;
        private final IngredientRepository ingredientRepository;
        private final PizzaRepository pizzaRepository;
        private final RecipeRepository recipeRepository;
        private final JwtUtil jwtUtil;
        private final ModelMapper modelMapper;

        public HttpStatus createIngredient(IngredientDto ingredientDto){
                ingredientRepository.save(modelMapper.map(ingredientDto, Ingredient.class));
                return HttpStatus.CREATED;
        }

        public HttpStatus createRecipe(List<Integer> ingredientsIdList) {
                Recipe recipe = new Recipe(findAllIngredient(ingredientsIdList));
                recipeRepository.save(recipe);
                return HttpStatus.CREATED;
        }

        public HttpStatus createPizza(PizzaDto pizzaDto) {
                Pizza pizza = new Pizza();
                modelMapper.map(pizzaDto, pizza);
                preparePizzaForSaving(pizza, pizzaDto);
                pizzaRepository.save(pizza);
                return HttpStatus.CREATED;
        }

        /**
         * Method finds all requested ingredients what are necessary for recipe.
         * @param ingredientsIdList list of ingredients id what will be found
         * @return list of founded ingredients
         */
        private List<Ingredient> findAllIngredient(List<Integer> ingredientsIdList) {
                return ingredientRepository.findAllById(ingredientsIdList);
        }

        private void preparePizzaForSaving(Pizza pizza, PizzaDto pizzaDto) {
                Recipe recipe = getRecipeById(pizzaDto.getRecipeId());
                pizza.setRecipe(recipe);
        }

        private Recipe getRecipeById(Integer recipeId) {
               return recipeRepository.findById(recipeId)
                       .orElseThrow(() -> new RecipeNotFoundException
                               ("cannot find recipe with this id"));
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
