package banking.fullstack.app.account;

import banking.fullstack.app.exceptionhandling.CodeMessage;
import banking.fullstack.app.exceptionhandling.CodeMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(path = "/hanover/api/v2")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {

        Iterable<Account> accounts = accountService.getAllAccounts();
        if (accounts.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", accounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CodeMessage exception = new CodeMessage(404, "Error: no accounts found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable Long accountId) {

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
        if (accounts.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", accounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CodeMessage exception = new CodeMessage(404, "Error: account with customer id " + customerId + " does not exist");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable Long customerId, @RequestBody Account account) {

        try {
            if (accountService.customerCheck(customerId)) {
                CodeMessageData response = new CodeMessageData(201, "Account created", accountService.createAccount(account));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            CodeMessage exception = new CodeMessage(404, "Error: customer not found");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            CodeMessage error = new CodeMessage(404, "Error: could not create account");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {

        if (accountService.accountCheck(accountId)) {
            accountService.updateAccount(account);
            CodeMessage response = new CodeMessage(200, "Customer account updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {

        if (accountService.accountCheck(accountId)) {
            accountService.deleteAccount(accountId);
            CodeMessage response = new CodeMessage(200, "Account deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CodeMessage exception = new CodeMessage(404, "Error: account with id " + accountId + " does not exist");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}