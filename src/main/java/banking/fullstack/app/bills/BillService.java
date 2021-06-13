package banking.fullstack.app.bills;

import banking.fullstack.app.account.Account;
import banking.fullstack.app.account.AccountRepository;
import banking.fullstack.app.customer.CustomerRepository;
import banking.fullstack.app.customer.CustomerService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    Logger billLog = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillRepo billRepo;

    public Iterable<Bill> getAllBills(){

        billLog.info("===== RETRIEVING ALL BILLS =====");
        return billRepo.findAll();
    }

    public List<Bill> getAllBillsByAccountId(Long id) {

        billLog.info("===== RETRIEVING ALL BILLS BY ACCOUNT ID =====");
        return billRepo.getBillByAccountId(id);
    }

    public Bill getBillByBillId(Long id) {

        billLog.info("===== RETRIEVING BILL BY BILL ID =====");
        return billRepo.findById(id).orElse(null);
    }

    public List<Bill> getAllBillsByCustomerId(Long customer_id) {

        billLog.info("===== RETRIEVING ALL BILLS BY CUSTOMER ID =====");
        List<Long> accountId = billRepo.getAccountIdThatMatchesCustomerId(customer_id);
        return billRepo.getBillsThatMatchAccountIdInBillWithAccountIdInAccountToUseAfterFindingCustomerByIdInAccount(accountId);
    }

    @Autowired AccountRepository accountRepository;
    public boolean accountCheck(Long accountId){

        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null;
    }

    public boolean billCheck(Long billId){

        Bill bill = billRepo.findById(billId).orElse(null);
        return bill != null;
    }

    public Bill createBill(Bill bill) {

        billLog.info("===== CREATING BILL =====");
        return billRepo.save(bill);
    }

    public void updateBill(Bill bill){

        billLog.info("===== UPDATING BILL =====");
        billRepo.save(bill);
    }

    public void deleteBill(Long id){

        billLog.info("===== DELETING BILL =====");
        billRepo.deleteById(id);
    }
}

