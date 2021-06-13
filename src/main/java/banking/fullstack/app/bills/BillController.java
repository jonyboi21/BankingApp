package banking.fullstack.app.bills;

import banking.fullstack.app.account.AccountRepository;
import banking.fullstack.app.account.AccountService;
import banking.fullstack.app.customer.CustomerRepository;
import banking.fullstack.app.customer.CustomerService;
import banking.fullstack.app.exceptionhandling.CodeData;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/hanover/api/v2")
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

    @GetMapping("/bills")
    public ResponseEntity<?> getAllBills() {

        Iterable<Bill> bills = billService.getAllBills();
        if (bills.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", bills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CodeMessage exception = new CodeMessage(404,"Error: no bills found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{accountId}/bills")
    public ResponseEntity<?> getAllBillsByAccount(@PathVariable Long accountId) {

        List<Bill> bills = billService.getAllBillsByAccountId(accountId);
        if(bills.isEmpty()){
            CodeMessage exception = new CodeMessage(404,"Error: account with id " + accountId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        CodeData response = new CodeData(200, bills);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bills/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Long billId){

        Bill bill = billService.getBillByBillId(billId);
        if(bill == null){
            CodeMessage exception = new CodeMessage(404,"Error: bill with id " + billId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        CodeData response = new CodeData(200, bill);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/bills")
    public ResponseEntity<?> getAllBillsByCustomerId(@PathVariable Long customerId){

        List<Bill> bills = billService.getAllBillsByCustomerId(customerId);
        if(bills.isEmpty()){
            CodeMessage exception = new CodeMessage(404,"Error: bill with customer id " + customerId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        CodeData response = new CodeData(200, bills);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountId}/bills")
    public ResponseEntity<?> createBill(@RequestBody Bill bill, @PathVariable Long accountId) {
        try {
            if (!billService.accountCheck(accountId)) {
                CodeMessage exception = new CodeMessage(404,"Error: account with id " + accountId + " does not exist");
                return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
            } else {
                Bill b1 = billService.createBill(bill);
                CodeMessageData response = new CodeMessageData(201, "Bill created", b1);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } } catch (Exception e){
            CodeMessage error = new CodeMessage(404, "Error: could not create bill");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/bills/{billId}")
    public ResponseEntity<?> updateBill(@RequestBody Bill bill, @PathVariable Long billId){

        if(!billService.billCheck(billId)){
            CodeMessage exception = new CodeMessage(404,"Error: bill with id " + billId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        billService.updateBill(bill);
        CodeMessage response = new CodeMessage(200, "Bill updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<?> deleteBill(@PathVariable Long billId){

        if(!billService.billCheck(billId)){
            CodeMessage exception = new CodeMessage(404,"Error: bill with id " + billId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

        billService.deleteBill(billId);
        CodeMessage response = new CodeMessage(200,"Bill deleted");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
