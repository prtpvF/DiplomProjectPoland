package pl.diplom.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.IngredientDto;
import pl.diplom.admin.dto.PersonDto;
import pl.diplom.admin.dto.PizzaDto;
import pl.diplom.admin.exception.PersonNotFoundException;
import pl.diplom.common.model.*;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.repository.IngredientRepository;
import pl.diplom.common.repository.PersonRepository;
import pl.diplom.common.repository.product.PizzaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

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
        private final IngredientService ingredientService;
        private final PersonOrderService personOrderService;
        private final ModelMapper modelMapper;


        public HttpStatus createIngredient(IngredientDto ingredientDto){
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientDto.getName());
                ingredient.setCost(ingredientDto.getCost());
                ingredient.setWeight(ingredientDto.getWeight());
                ingredientRepository.save(ingredient);
                return HttpStatus.CREATED;
        }

        public HttpStatus deleteIngredient(Integer ingredientId) {
                ingredientService.isIngredientExists(ingredientId);
                ingredientRepository.deleteById(ingredientId);
                return OK;
        }

        public HttpStatus updateIngredient(IngredientDto ingredientDto,
                                           Integer ingredientNeedToBeUpdatedId) {
                return ingredientService.updateIngredientModel(ingredientDto,
                        ingredientNeedToBeUpdatedId);
        }

        public void banPerson(Integer personId) {
                Person person = findPersonById(personId);
                person.setActive(false);
                personRepository.save(person);
        }

        public void unbanPerson(Integer personId) {
                Person person = findPersonById(personId);
                person.setActive(true);
                personRepository.save(person);
        }

        public PersonDto getPersonDtoById(Integer personId) {
                Person person = findPersonById(personId);
                return convertPersonToDto(person);
        }

        public HttpStatus updateAddressForOrder(Integer orderNeedToBeUpdatedId,
                                                String addressName) {
                return personOrderService.updateAddressForPersonOrder(orderNeedToBeUpdatedId,
                        addressName);
        }

        public HttpStatus removeOrderFromStory(Integer orderId) {
              return personOrderService.removeOrderFromPersonHistory(orderId);
        }

        public HttpStatus updatePersonOrderStatus(Integer orderId,
                                                  String statusName) {
              return personOrderService.updatePersonOrderStatus(statusName , orderId);
        }

        private Person findPersonById(Integer personId) {
                return personRepository.findById(personId)
                        .orElseThrow(() -> new PersonNotFoundException(
                                "cannot find person with this id"
                        ));
        }

        private PersonDto convertPersonToDto(Person person) {
                PersonDto personDto = new PersonDto();
                personDto.setId(person.getId());
                personDto.setRole(person.getRole().getRoleName());
                personDto.setEmail(person.getEmail());
                personDto.setFirstName(person.getFirstName());
                personDto.setLastName(person.getLastName());
                personDto.setUsername(person.getUsername());

                if(!person.getAddresses().isEmpty()) {
                        personDto.setAddressIdList(retrieveAllIdFromPersonAddressList(person.getAddresses()));
                }
                return personDto;
        }

        private List<Integer> retrieveAllIdFromPersonAddressList(List<Address> addressList) {
                List<Integer> idList = new ArrayList<>();

                for(Address address : addressList) {
                        idList.add(address.getId());
                }
                return idList;
        }
}