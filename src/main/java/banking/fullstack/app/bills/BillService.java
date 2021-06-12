package banking.fullstack.app.bills;

import banking.fullstack.app.account.AccountRepository;
import banking.fullstack.app.customer.CustomerRepository;
import banking.fullstack.app.customer.CustomerService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillController billController;

    @Autowired
    private AccountRepository accountRepository;


    public Iterable<Bill> getAllByAccountId(Long accountId) {
        logger.info("SUCCESSFULLY RETRIEVED ALL BILLS BY ACCOUNT ID: " + accountId);
        return billRepo.getAllBillsByAccountId(accountId);
    }

    public Iterable<Bill> getAllByCustomerId(Long customerId) {

        logger.info("SUCCESSFULLY RETRIEVED ALL BILLS BY Customer ID: " + customerId);
        return billRepo.getAllBillsByCustomerId(customerId);
    }

    public Optional<Bill> getById(Long billId) {
        if (!billRepo.existsById(billId)) {
            logger.info("CANNOT RETRIEVE BILL THAT DOESN'T EXISTS");
        } else {

            logger.info("SUCCESSFULLY RETRIEVED Bill WITH THE ID: " + billId);
        }
        return billRepo.findById(billId);
    }


    public void createBill(Bill bill) {
        accountRepository.findById(bill.getAccountId()).get()
                .setBalance(accountRepository.findById(bill.getAccountId()).get().getBalance() - bill.getPaymentAmount());


        logger.info("BILL SUCCESSFULLY CREATED");
        billRepo.save(bill);
    }

    public void updateBill(Bill bill, Long billId) {
        accountRepository.findById(billRepo.findById(billId).get().getAccountId()).get().setBalance(
                accountRepository.findById(billRepo.findById(billId).get().getAccountId()).get().getBalance() + billRepo.findById(billId).get().getPaymentAmount());

        accountRepository.findById(bill.getAccountId()).get()
                .setBalance( accountRepository.findById(bill.getAccountId()).get().getBalance() - bill.getPaymentAmount());

        if (!(billRepo.existsById(bill.getId())))
        {
            logger.info("CANNOT UPDATE NON-EXISTING BILL");
            throw new ResourceNotFoundException("ERROR");
        } else {
            bill.setId(billId);
            logger.info("BILL WITH ID: " + billId + " SUCCESSFULLY UPDATED");
            billRepo.save(bill);}

    }

    public void deleteBill(Long billId) {
        accountRepository.findById(billRepo.findById(billId).get().getAccountId()).get().setBalance(
                accountRepository.findById(billRepo.findById(billId).get().getAccountId()).get().getBalance() + billRepo.findById(billId).get().getPaymentAmount());

        if (!(billRepo.existsById(billId))) {
//            logger.info("CANNOT DELETE NON-EXISTING BILL");
            throw new ResourceNotFoundException("Bill DOES NOT EXIST");
        }else
            billRepo.deleteById(billId);
//        logger.info("BILL WITH ID: " + billId + " REMOVED FROM SYSTEM");
    }
}
