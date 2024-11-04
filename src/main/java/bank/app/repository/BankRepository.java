package bank.app.repository;

import bank.app.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank getBankById(Long Id);
    boolean deleteBankById(Long id);
}
