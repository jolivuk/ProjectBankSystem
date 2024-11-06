package bank.app.repository;

import bank.app.model.entity.Account;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    List<Account> getAccountByUserId(Long userId);
//    void softDeleteAccount(Long accountId, bank.app.model.enums.Status deleted);

}
