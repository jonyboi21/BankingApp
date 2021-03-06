package banking.fullstack.app.repositories;

import banking.fullstack.app.models.Withdrawal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {

    @Query(value = "SELECT * FROM Withdrawal WHERE payerId = ?1", nativeQuery = true)
    List<Withdrawal> getWithdrawalByAccountId(Long accountId);
}
