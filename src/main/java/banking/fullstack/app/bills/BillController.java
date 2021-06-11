package banking.fullstack.app.bills;

import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import banking.fullstack.app.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    public void verifyAccount(Long accountId) throws ResourceNotFoundException {
        Optional<Account> account = accountService.getById(accountId);
        if (!account.isPresent()) {
            throw new ResourceNotFoundException("error fetching bills");
        }
    }

    public void verifyCreate(Long accountId) throws ResourceNotFoundException {
        Optional<Account> account = accountService.getById(accountId);
        if (!(account.isPresent())) {
            logger.info("ERROR CREATING BILL");
            throw new ResourceNotFoundException("Error creating bill: Account not found");
        }
    }

    public void verifyBill(Long billId) throws ResourceNotFoundException {
        Optional<Bill> bill1 = billRepo.findById(billId);
        if (bill1.isEmpty()) {
            throw new ResourceNotFoundException("error fetching bills with id: " + billId);
        }
    }

    public void verifyCustomer(Long customerId) throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException("error fetching bills");
        }
    }

    public void verifyUpdate(Long billId) throws ResourceNotFoundException {
        Optional<Bill> bill1 = billRepo.findById(billId);
        if (bill1.isEmpty()) {
            logger.info("ERROR UPDATING BILL WITH ID: " + billId);
            throw new ResourceNotFoundException("Bill ID: " + billId + " does not exist");
        }
    }

    @RequestMapping(value = "/accounts/{accountId}/bills", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBillsByAccountId(@PathVariable Long accountId) {
        verifyAccount(accountId);
        Iterable<Bill> a = billService.getAllByAccountId(accountId);
        SuccessfulResponseIterable successfulResponseIterable = new SuccessfulResponseIterable(HttpStatus.OK.value(), null, a);
        return new ResponseEntity<>(successfulResponseIterable, HttpStatus.OK);
    }

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBillById(@PathVariable Long billId) {
        verifyBill(billId);
        SuccessfulResponseObject successfulResponseObject = new SuccessfulResponseObject(HttpStatus.OK.value(), null, billService.getById(billId));

        return new ResponseEntity<>(successfulResponseObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{customerId}/bills", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBillsByCustomerId(@PathVariable Long customerId) {
        verifyCustomer(customerId);
        Iterable<Bill> c = billService.getAllByCustomerId(customerId);
        SuccessfulResponseIterable successfulResponseIterable = new SuccessfulResponseIterable(HttpStatus.OK.value(), null, c);
        return new ResponseEntity<>(successfulResponseIterable, HttpStatus.OK);
    }

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBill(@RequestBody Bill bill, @PathVariable Long billId) {
        verifyUpdate(billId);

        billService.updateBill(bill, billId);
        SuccessfulResponseObject successfulResponseObject = new SuccessfulResponseObject(202, "Accepted bill modification", null);
        return new ResponseEntity<>(successfulResponseObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{accountId}/bills", method = RequestMethod.POST)
    public ResponseEntity<?> createBill( @PathVariable Long accountId, @Validated @RequestBody Bill bill) {
        verifyCreate(accountId);
        billService.createBill(bill);
        SuccessfulResponseObject successfulResponseObject = new SuccessfulResponseObject(HttpStatus.CREATED.value(), "Created bill and added it to the account", bill);
        return new ResponseEntity<>(successfulResponseObject, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBill(@PathVariable Long billId) {
        Optional<Bill> bill = billService.getById(billId);
        if (bill.isEmpty()) {
            logger.info("CANNOT DELETE NON-EXISTING BILL");
            throw new ResourceNotFoundException("This id: " + billId + " does not exist in bills");
        } else {
            logger.info("BILL WITH ID: " + billId + " SUCCESSFULLY UPDATED");
            billService.deleteBill(billId);
            SuccessfulResponseOptional successfulResponseOptional = new SuccessfulResponseOptional(204, null, null);
            return new ResponseEntity<>(successfulResponseOptional, HttpStatus.OK);
        }
    }
}
