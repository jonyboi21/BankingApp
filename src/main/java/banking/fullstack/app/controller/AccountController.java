package banking.fullstack.app.controller;

import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import banking.fullstack.app.models.Account;
import banking.fullstack.app.repositories.AccountRepository;
import banking.fullstack.app.repositories.CustomerRepository;
import banking.fullstack.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
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

        try {
            if (accountService.customerCheck(customerId)) {
                CodeMessageData response = new CodeMessageData(201, "Account created", accountService.createAccount(account));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            CodeMessage exception = new CodeMessage(404, "Error: customer with id " + customerId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            CodeMessage error = new CodeMessage(400, "Error: could not create account");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account")
    public ResponseEntity<?> getAllAccounts() {

        Iterable<Account> accounts = accountService.getAllAccounts();
        if (accounts.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", accounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CodeMessage exception = new CodeMessage(404, "Error: no accounts found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getAccountByAccountId(@PathVariable Long accountId) {

        Optional<Account> account = accountService.getAccountByAccountId(accountId);
        if (account.isEmpty()) {
            CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        CodeMessageData response = new CodeMessageData(200, "Success", account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<?> getAllAccountsByCustomer(@PathVariable Long customerId) {

        Iterable<Account> accounts = accountService.getAllAccountsByCustomer(customerId);
        if (customerRepository.existsById(customerId)) {
            CodeMessageData response = new CodeMessageData(200, "Success", accounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CodeMessage exception = new CodeMessage(404, "Error: customer with id " + customerId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {

        try {
            if (accountService.accountCheck(accountId)) {
                accountService.updateAccount(account, accountId);
                CodeMessage response = new CodeMessage(200, "Customer account updated");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            CodeMessage error = new CodeMessage(400, "Error: could not update account");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

        @DeleteMapping("/account/{accountId}")
        public ResponseEntity<?> deleteAccount (@PathVariable Long accountId){
            if (accountService.accountCheck(accountId)) {
                accountService.deleteAccount(accountId);
                CodeMessage response = new CodeMessage(200, "Account deleted");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
}