package bank.app.repository;

import bank.app.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card getCardById(Long Id);
    boolean deleteCardById(Long id);
}
