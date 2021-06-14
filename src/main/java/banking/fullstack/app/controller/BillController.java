package banking.fullstack.app.controller;

import banking.fullstack.app.models.Bill;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.BillRepository;
import banking.fullstack.app.repositories.CustomerRepository;
import banking.fullstack.app.services.AccountService;
import banking.fullstack.app.services.BillService;
import banking.fullstack.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void getAllBills() {
       billService.getAllBills();
    }

    @GetMapping("/accounts/{accountId}/bills")
    public void getAllBillsByAccount(@PathVariable Long accountId) {
        billService.getBillsByAccountId(accountId);
    }

    @GetMapping("/bills/{billId}")
    public void getBillById(@PathVariable Long billId){
        billService.getBillByBillId(billId);
    }

    @GetMapping("/customers/{customerId}/bills")
    public void getAllBillsByCustomerId(@PathVariable Long customerId){
        billService.getAllBillsByCustomerId(customerId);
    }

    @PostMapping("/accounts/{accountId}/bills")
    public void createBill(@RequestBody Bill bill) {
      billService.createBill(bill);
    }

    @PutMapping("/bills/{billId}")
    public void updateBill(@RequestBody Bill bill){
        billService.updateBill(bill);
    }

    @DeleteMapping("/bills/{billId}")
    public void deleteBill(@PathVariable Long billId){
        billService.deleteBill(billId);
    }
}
