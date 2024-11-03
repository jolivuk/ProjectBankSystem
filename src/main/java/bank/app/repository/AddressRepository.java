package bank.app.repository;

import bank.app.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address getAddressById(Long Id);
    List<Address> getAllAddressByCountry(String country);

}
