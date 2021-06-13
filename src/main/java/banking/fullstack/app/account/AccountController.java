package banking.fullstack.app.account;

import banking.fullstack.app.customer.Customer;
import banking.fullstack.app.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;


    @PostMapping("customer/{customerId}/account")
    public ResponseEntity<?> createAccount(@PathVariable Long customerId, @RequestBody Account account) {
        if (customerRepository.existsById(customerId)) {
            accountService.createAccount(account);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account/")
    public Iterable<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/account/{accountId}")
    public Optional<Account> getAccountByAccountId(@PathVariable Long accountId) {
        return accountService.getAccountByAccountId(accountId);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public Iterable<Account> getAllAccountsByCustomer(@PathVariable Long customerId) {
        if (customerRepository.existsById(customerId)) {
            return accountService.getAllAccountsByCustomer(customerId);
        } else {
            return null;
        }
    }
    //need to make the customer id match an existing customer id

    @PutMapping("/account/{accountId}")
    public void updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
            accountService.updateAccount(account, accountId);
    }

        @DeleteMapping("/account/{accountId}")
        public void deleteAccount (@PathVariable Long accountId){
            accountService.deleteAccount(accountId);

    }
}