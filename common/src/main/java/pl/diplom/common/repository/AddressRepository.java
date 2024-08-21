package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

        Optional<Address> findByAddressAndPerson(String address, Person person);

        Optional<Address> findAddressByAddress(String address);
}
