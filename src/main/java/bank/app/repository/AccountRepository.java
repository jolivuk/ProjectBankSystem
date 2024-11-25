package bank.app.repository;

import bank.app.model.entity.Account;
import bank.app.model.enums.Role;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user u WHERE u.role = :userRole " +
            "AND u.status = 'ACTIVE' AND a.status = 'ACTIVE'")
    List<Account> findByUserRole(@Param("userRole") Role userRole);
}