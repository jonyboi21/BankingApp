package banking.fullstack.app.controller;

import banking.fullstack.app.exceptionhandling.CodeData;
import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import banking.fullstack.app.models.Bill;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.BillRepository;
import banking.fullstack.app.repositories.CustomerRepository;
import banking.fullstack.app.services.AccountService;
import banking.fullstack.app.services.BillService;
import banking.fullstack.app.services.CustomerService;
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
    private BillRepository billRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

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
        List<Bill> bills = billService.getBillsByAccountId(accountId);
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
            if (!accountService.accountCheck(accountId)) {
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
