package bank.app.repository;

import bank.app.model.entity.Account;
import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);

    @Query("SELECT u.password FROM User u  WHERE u.username = :username ")
    String findPasswordByUsername(@Param("username") String username);

    List<User> findAllByManagerId(Long managerId);



}
