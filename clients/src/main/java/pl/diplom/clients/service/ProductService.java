package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.clients.exception.PizzaNotFoundException;
import pl.diplom.common.model.product.Pizza;
import pl.diplom.common.repository.product.PizzaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {

        private final PizzaRepository pizzaRepository;

        public List<Pizza> getConvertedPizzaList(List<Integer> pizzaIdList) {
            List<Pizza> convertedPizzaList = new ArrayList<>();
            for (Integer pizzaId : pizzaIdList) {
                convertedPizzaList.add(pizzaRepository
                        .findById(pizzaId)
                        .orElseThrow(() ->
                                new PizzaNotFoundException("Pizza not found. Try again")));
            }
            return convertedPizzaList;
        }
}
