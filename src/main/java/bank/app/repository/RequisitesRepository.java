package bank.app.repository;

import bank.app.model.entity.Requisites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitesRepository extends JpaRepository<Requisites, Long> {
    Requisites getRequisitesById(Long Id);
    boolean deleteRequisitesById(Long id);
}
