package bank.app.repository;

import bank.app.model.entity.TransactionType;
import bank.app.model.enums.TransactionTypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    Optional<TransactionType> findByTransactionTypeName(TransactionTypeName transactionTypeName);
}

