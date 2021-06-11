package banking.fullstack.app.withdrawal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {

    @Query(value = "SELECT * FROM Withdrawal WHERE payer_id = ?1", nativeQuery = true)
    List<Withdrawal> getWithdrawalByAccountId(Long accountId);
}
