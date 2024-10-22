package bank.app.repository;

import bank.app.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientJpa extends JpaRepository<Client,Long> {

    List<Client> findByFirstName(String firstName);
    List<Client> findByLastName(String lastName);
}
