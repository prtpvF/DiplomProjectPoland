package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.PortionDto;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Portion;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PortionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortionService {

        private final PortionRepository portionRepository;
        private final IngredientRepository ingredientRepository;

        public Portion findOrCreate(Integer ingredientId,
                                    int weight) {
                Portion newPortion = new Portion();
                mapAdditionalFields(newPortion, ingredientId, weight);
                newPortion = portionRepository.save(newPortion);
                System.out.println(newPortion.getId());
                return newPortion;
        }

        public List<Portion> findAllOrCreate(List<PortionDto> portionsDto) {
            List<Portion> portions = new ArrayList<>();
            for(PortionDto portionDto : portionsDto) {
                portions.add(findOrCreate(
                        portionDto.getIngredientId(),
                        portionDto.getWeight()
                ));
            }
            return portions;
        }

        private Portion mapAdditionalFields(Portion portion,
                                            Integer ingredientId,
                                            int weight) {
            portion.setIngredient(ingredientRepository
                    .findById(ingredientId)
                    .orElseThrow(() -> new EntityNotFoundException("cannot find portion with id: " + ingredientId)));
            portion.setWeight(weight);
            portion.setPizza(null);
            return portion;
        }

        private Ingredient findIngredientById(Integer ingredientId) {
            return ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "cannot find an ingredient with this id"
                    ));
        }
}