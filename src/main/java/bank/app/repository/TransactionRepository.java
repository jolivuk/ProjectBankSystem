package bank.app.repository;

import bank.app.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction getTransactionById(Long Id);
    boolean deleteTransactionById(Long id);
    List<Transaction> findBySenderIdOrReceiverId(Long accountId, Long accountId1);
  //  List<Transaction> findByRequisitesAccountIdAndCreatedAtBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
