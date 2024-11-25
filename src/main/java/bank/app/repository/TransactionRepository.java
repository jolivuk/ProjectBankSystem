package bank.app.repository;

import bank.app.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderIdOrReceiverId(Long accountId, Long accountId1);
    @Query("SELECT t FROM Transaction t " +
            "LEFT JOIN FETCH t.sender s " +
            "LEFT JOIN FETCH t.receiver r " +
            "LEFT JOIN FETCH t.transactionType tt " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "AND (s.id = :accountId OR r.id = :accountId) " +
            "AND t.transactionStatus = 'COMPLETED' " +
            "ORDER BY t.transactionDate DESC")
    List<Transaction> findByDateRangeAndAccount(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("accountId") Long accountId
    );

}

