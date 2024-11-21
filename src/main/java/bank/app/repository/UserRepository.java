package bank.app.repository;

import bank.app.model.entity.User;
import bank.app.model.enums.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRole(Role role);

    @EntityGraph(attributePaths = "manager")
    @Query("select u from User u where u.id=:userId")
    Optional<User> findAllByIdWithManager(Long userId);

}
