package pl.diplom.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.diplom.common.model.Address;
import pl.diplom.common.model.Person;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

        @Query("SELECT a FROM Address a WHERE a.address LIKE %:address% AND a.person = :person")
        Optional<Address> findByAddressAndPerson(@Param("address") String address, @Param("person") Person person);

        Optional<Address> findAddressByAddress(String address);
}
