package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        public IngredientDto getIngredientById(Integer id) {
            Ingredient ingredient = ingredientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Ingredient with id " + id + " not found"));
            return convertToDto(ingredient);
        }

        public Page<IngredientDto> getAllIngredients(Pageable pageable) {
            Page<Ingredient> ingredients = ingredientRepository.findAll(pageable);
            Page<IngredientDto> dtoPage = ingredients.map(ingredient -> convertToDto(ingredient));
            return dtoPage;
        }

        public List<IngredientDto> getAllIngredientsList() {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            List<IngredientDto> dtoPage = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                dtoPage.add(convertToDto(ingredient));
            }
            return dtoPage;
        }

        public void isIngredientExists(Integer id) {
            ingredientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find ingredient"));
        }

        public HttpStatus updateIngredientModel(IngredientDto ingredientDto,
                                                Integer ingredientNeedToBeUpdatedId) {
            Ingredient ingredientNeedToBeUpdated = ingredientRepository.findById(ingredientNeedToBeUpdatedId)
                            .orElseThrow(() -> new EntityNotFoundException("cannot find ingredient with this id"));

            ingredientNeedToBeUpdated.setName(ingredientDto.getName());
            ingredientNeedToBeUpdated.setCost(ingredientDto.getCost());
            ingredientNeedToBeUpdated.setId(ingredientNeedToBeUpdated.getId());
            ingredientNeedToBeUpdated.setPizza(ingredientNeedToBeUpdated.getPizza());
            ingredientRepository.save(ingredientNeedToBeUpdated);
            return OK;
        }

        public IngredientDto convertToDto(Ingredient ingredient) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setId(ingredient.getId());
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setCost(ingredient.getCost());
            return ingredientDto;
        }
}