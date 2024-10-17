package pl.diplom.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.admin.dto.AddressDto;
import pl.diplom.admin.dto.PersonOrderDto;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;
import pl.diplom.common.model.PersonOrder;
import pl.diplom.common.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

        private final AddressRepository addressRepository;

        public Address findOrCreateAddress(String addressName, Person person) {
            Optional<Address> foundedd = addressRepository.findAddressByAddress(addressName);
            if(!foundedd.isPresent()) {
                return foundedd.get();
            }
             else {
                Address address =  addressRepository.save(new Address(person, addressName));
                return address;
            }
        }

        private AddressDto convertAddressToDto(Address address) {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setAddress(address.getAddress());
            addressDto.setOwnerId(address.getPerson().getId());

            List<Integer> idList = new ArrayList<>();

            for(PersonOrder order : address.getOrders()) {
                idList.add(order.getId());
            }
            addressDto.setOrderIdList(idList);
            return addressDto;
        }
}