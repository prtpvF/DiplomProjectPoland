package pl.diplom.admin.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.PortionDto;
import pl.diplom.common.model.Ingredient;
import pl.diplom.common.model.Portion;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PortionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortionService {

        private final PortionRepository portionRepository;
        private final IngredientRepository ingredientRepository;

        @Transactional
        public PortionDto findOrCreate(Ingredient ingredient,
                                    int weight) {
               Portion portion = portionRepository
                       .findPortionByIngredientAndWeight(ingredient, weight)
                       .orElse(null);

               if (portion == null) {
                   Portion newPortion = mapAdditionalFields(ingredient.getId(), weight);
                   newPortion = portionRepository.save(newPortion);
                   return convertToDto(newPortion);
               }
            return convertToDto(portion);
        }

        @Transactional
        public List<PortionDto> findAllOrCreate(List<PortionDto> portionsDto) {

            List<PortionDto> newPortions = new ArrayList<>();

            for(PortionDto portionDto : portionsDto) {

               newPortions.add(findOrCreate(findIngredientById(
                               portionDto.getIngredientId()
                       ),
                       portionDto.getWeight()));
            }
            return newPortions;
        }

        private Portion mapAdditionalFields(
                                            Integer ingredientId,
                                            int weight) {
            Portion portion = new Portion();
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

        private PortionDto findPortion(int ingredientId, int weight) {
            Optional<Portion> portion = portionRepository
                    .findPortionByIngredientAndWeight(findIngredientById(ingredientId), weight);

            if(portion.isPresent()) {
                PortionDto portionDto = new PortionDto();
                portionDto = convertToDto(portion.get());
                return portionDto;
            }
            return null;
        }

        private PortionDto convertToDto(Portion portion) {
            PortionDto dto = new PortionDto();
            dto.setId(portion.getId());
            dto.setWeight(portion.getWeight());
            dto.setIngredientId(portion.getIngredient().getId());
            return dto;
        }

        private Portion convertFromDto(PortionDto dto) {
            Portion portion = new Portion();
            portion.setIngredient(
                   findIngredientById(
                           dto.getIngredientId())
            );
            portion.setWeight(dto.getWeight());
            portion.setPizza(null);
            return portion;
        }
}