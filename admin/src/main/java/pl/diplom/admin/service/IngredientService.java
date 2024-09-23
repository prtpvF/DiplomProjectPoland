package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.exception.IngredientNotFoundException;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Person;
import pl.diplom.common.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class IngredientService {

        private final IngredientRepository ingredientRepository;
        private final KafkaTemplate<String, String> kafkaTemplate;

        public Ingredient getIngredientById(Integer id) {
            return ingredientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Ingredient with id " + id + " not found"));
        }

        public List<IngredientDto> getAllIngredients() {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            List<IngredientDto> dtos = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                IngredientDto dto = new IngredientDto();
                dto.setName(ingredient.getName());
                dto.setCost(ingredient.getCost());
                dto.setId(ingredient.getId());
                dtos.add(dto);
            }
            return dtos;
        }

        public List<Ingredient> getAllIngredientsById(List<Integer> ids) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
            if(ingredients.isEmpty()) {
                throw new IngredientNotFoundException(
                        "cannot find ingredients with ids " + ids);
            }
            return ingredients;
        }

        public void isIngredientExists(Integer id) {
            ingredientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find ingredient"));
        }

        public HttpStatus updateIngredientModel(IngredientDto ingredientDto,
                                                Integer ingredientNeedToBeUpdatedId) {
            Ingredient ingredientNeedToBeUpdated = getIngredientById(ingredientNeedToBeUpdatedId);

           ingredientNeedToBeUpdated.setName(ingredientDto.getName());
           ingredientNeedToBeUpdated.setCost(ingredientDto.getCost());
            ingredientNeedToBeUpdated.setId(ingredientNeedToBeUpdated.getId());
            ingredientNeedToBeUpdated.setPizza(ingredientNeedToBeUpdated.getPizza());
            ingredientRepository.save(ingredientNeedToBeUpdated);
            return OK;
        }

        public void checkIngredientQuantity(Ingredient ingredient) {
            if(ingredient.getQuantity()<20) {
                String toEmail = getRandomAdminEmail();
                kafkaTemplate.send("admin-topic", toEmail);
            }
        }

        private String getRandomAdminEmail() {
            List<Person> admins = new ArrayList<>();
            int random = (int) (Math.random() * admins.size());
            return admins.get(random).getEmail();
        }
}
