package banking.fullstack.app.repositories;

import banking.fullstack.app.models.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends CrudRepository<Deposit,Long> {

    @Query(value = "SELECT * FROM Deposit WHERE payeeId = ?1", nativeQuery = true)
    List<Deposit> getDepositsByAccountId(Long accountId);

    @Query(value = "SELECT * FROM Deposit WHERE payeeId = ?1", nativeQuery = true)
    Optional<Deposit> getDepositByAccountId(Long accountId);
}
