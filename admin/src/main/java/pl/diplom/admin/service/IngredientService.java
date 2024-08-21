package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.repository.IngredientRepository;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class IngredientService {

        private final IngredientRepository ingredientRepository;
        private final ModelMapper modelMapper;

        public Ingredient getIngredientById(Integer id) {
            return ingredientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Ingredient with id " + id + " not found"));
        }

        public List<Ingredient> getAllIngredientsById(List<Integer> ids) {
            return ingredientRepository.findAllById(ids);
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
}
