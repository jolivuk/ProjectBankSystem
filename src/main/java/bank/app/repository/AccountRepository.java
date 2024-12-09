package bank.app.repository;

import bank.app.model.entity.Account;
import bank.app.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user u WHERE u.role = :userRole " +
            "AND u.status = 'ACTIVE' AND a.status = 'ACTIVE'")
    List<Account> findByUserRole(@Param("userRole") Role userRole);
}