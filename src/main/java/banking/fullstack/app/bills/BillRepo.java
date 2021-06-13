package banking.fullstack.app.bills;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends CrudRepository<Bill, Long> {

    @Query(value = "SELECT * FROM bill WHERE accountId = ?1", nativeQuery = true)
    List<Bill> getBillByAccountId(Long account_id);

    @Query(value = "SELECT id FROM account WHERE customerId = ?1", nativeQuery = true)
    List<Long> getAccountIdThatMatchesCustomerId(Long customer_id);

    @Query(value = "SELECT * FROM bill as e WHERE e.accountId IN (:accountId)", nativeQuery = true)
    List<Bill> getBillsThatMatchAccountIdInBillWithAccountIdInAccountToUseAfterFindingCustomerByIdInAccount(@Param("accountId") List<Long> accountId);
}
