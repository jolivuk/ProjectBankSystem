package bank.app.repository;

import bank.app.model.entity.PrivateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateInfoRepository extends JpaRepository<PrivateInfo, Long> {
}
