package bank.app.repository;

import bank.app.model.entity.Account;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM accounts a " +
            "JOIN users u ON u.user_id = a.user_id " +
            "WHERE u.status = 'ACTIVE' AND a.status = 'ACTIVE' " +
            "AND u.role = :userRole " +
            "ORDER BY a.created_at ASC LIMIT 1",
            nativeQuery = true)
    Optional<Account> findFirstByUserRoleNative(@Param("userRole") String userRole);
}