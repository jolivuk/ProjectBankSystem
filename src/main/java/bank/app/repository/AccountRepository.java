package bank.app.repository;

import bank.app.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountById(Long id);
    List<Account> getAccountByUserId(Long userId);
}
